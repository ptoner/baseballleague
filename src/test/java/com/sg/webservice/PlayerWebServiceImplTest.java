package com.sg.webservice;


import com.sg.commandmodel.player.createplayer.CreatePlayerCommandModel;
import com.sg.commandmodel.player.editplayer.EditPlayerCommandModel;
import com.sg.dto.Player;
import com.sg.dto.PlayerPosition;
import com.sg.dto.Position;
import com.sg.dto.Team;
import com.sg.service.PlayerPositionService;
import com.sg.service.PlayerService;
import com.sg.service.PositionService;
import com.sg.service.TeamService;
import com.sg.viewmodel.player.createplayer.CreatePlayerViewModel;
import com.sg.viewmodel.player.createplayer.CreatePositionViewModel;
import com.sg.viewmodel.player.createplayer.CreateTeamViewModel;
import com.sg.viewmodel.player.editplayer.EditPlayerViewModel;
import com.sg.viewmodel.player.editplayer.EditPositionViewModel;
import com.sg.viewmodel.player.editplayer.EditTeamViewModel;
import com.sg.viewmodel.player.playerlist.PlayerListViewModel;
import com.sg.viewmodel.player.playerlist.PlayerViewModel;
import com.sg.viewmodel.player.playerprofile.PlayerProfileViewModel;
import com.sg.viewmodel.player.playerprofile.PositionViewModel;
import com.sg.webservice.interfaces.PlayerWebService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-applicationContext.xml"})
@Rollback
@Transactional
public class PlayerWebServiceImplTest {

    @Inject
    PlayerWebService playerWebService;

    @Inject
    PlayerService playerService;

    @Inject
    TeamService teamService;

    @Inject
    PositionService positionService;

    @Inject
    PlayerPositionService playerPositionService;


    @Test
    public void getPlayerListViewModel() {

        //Arrange
        List<Player> players = createTestPlayers(15);


        //Act
        PlayerListViewModel playerListViewModel = playerWebService.getPlayerListViewModel(5, 0, 5);


        //Assert
        assert playerListViewModel.getPlayers().size() == 5;

        //Verify we're on the right page number.
        assert playerListViewModel.getPageNumber() == 1;

        //Verify we have the right number of page numbers
        assert playerListViewModel.getPageNumbers().size() == 5;

        //Verify start/end of page numbers are correct
        assert playerListViewModel.getPageNumbers().get(0) == 1;
        assert playerListViewModel.getPageNumbers().get(4) == 5;


        int counter=0;
        for (PlayerViewModel playerViewModel : playerListViewModel.getPlayers()) {
            assert ("Pat"+counter).equals(playerViewModel.getFirst());
            assert ("Toner"+counter).equals(playerViewModel.getLast());

            Player matchingPlayer = players.get(counter);

            //Get team from team service. Doing this because team is currently lazy loaded.
            //So test will pass even if in future it becomes not lazy loaded.
            Team team = teamService.read(matchingPlayer.getTeam().getId());


            assert team.getNickname().equals(playerViewModel.getTeamName());
            assert team.getId().equals(playerViewModel.getTeamId());

            counter++;
        }



    }


    @Test
    public void getPlayerProfileViewModel() {

        //Arrange
        Player player = new Player();

        player.setFirstName("Pat");
        player.setLastName("Toner");
        player.setHomeTown("Arizona");


        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        Team createdTeam = teamService.create(team);

        player.setTeam(createdTeam);

        Player createdPlayer = playerService.create(player);


        //Create test positions
        Position firstBase = new Position();
        firstBase.setName("1B");

        Position secondBase = new Position();
        secondBase.setName("2B");

        //Save test positions
        firstBase = positionService.create(firstBase);
        secondBase = positionService.create(secondBase);


        //Associate player with positions
        PlayerPosition playerPositionFirstBase = new PlayerPosition();
        playerPositionFirstBase.setPlayer(createdPlayer);
        playerPositionFirstBase.setPosition(firstBase);


        PlayerPosition playerPositionSecondBase = new PlayerPosition();
        playerPositionSecondBase.setPlayer(createdPlayer);
        playerPositionSecondBase.setPosition(secondBase);

        playerPositionService.create(playerPositionFirstBase);
        playerPositionService.create(playerPositionSecondBase);


        //Act
        PlayerProfileViewModel playerProfileViewModel = playerWebService.getPlayerProfileViewModel(createdPlayer.getId());


        //Assert
        assert playerProfileViewModel.getId().equals(createdPlayer.getId());
        assert playerProfileViewModel.getFirst().equals(createdPlayer.getFirstName());
        assert playerProfileViewModel.getLast().equals(createdPlayer.getLastName());

        assert playerProfileViewModel.getTeamId().equals(createdTeam.getId());
        assert playerProfileViewModel.getTeamName().equals(createdTeam.getCity() + " " + createdTeam.getNickname());


        boolean containsFirstBase = false;
        boolean containsSecondBase = false;
        for (PositionViewModel positionViewModel : playerProfileViewModel.getPositions()) {
            if ("1B".equals(positionViewModel.getName())) containsFirstBase = true;
            if ("2B".equals(positionViewModel.getName())) containsSecondBase = true;
        }

        assert containsFirstBase == true;
        assert containsSecondBase == true;



    }


    @Test
    public void getCreatePlayerViewModel() {

        //Arrange
        List<Position> positions = createTestPositions(15);
        List<Team> teams = createTestTeams(15);


        //Act
        CreatePlayerViewModel createPlayerViewModel = playerWebService.getCreatePlayerViewModel();

        //Assert
        assert createPlayerViewModel.getPositions().size() == positions.size();
        assert createPlayerViewModel.getTeams().size() == teams.size();

        for (CreatePositionViewModel createPositionViewModel : createPlayerViewModel.getPositions()) {
            assert  createPositionViewModel.getId() != null;
            assert createPositionViewModel.getName() != null;
        }

        for (CreateTeamViewModel createTeamViewModel : createPlayerViewModel.getTeams()) {
            assert createTeamViewModel.getId() != null;
            assert createTeamViewModel.getName() != null;
        }


    }


    @Test
    public void saveCreatePlayerCommandModel() {

        //Arrange
        Team createdTeam = createTestTeam();
        List<Position> createdPositions = createTestPositions(2);



        //In the web side of things spring is going to handle this part for us.
        CreatePlayerCommandModel createPlayerCommandModel = new CreatePlayerCommandModel();
        createPlayerCommandModel.setFirst("Pat");
        createPlayerCommandModel.setLast("Toner");
        createPlayerCommandModel.setHometown("Virginia");
        createPlayerCommandModel.setTeamId(createdTeam.getId());

        Long[] positionIds = new Long[2];
        positionIds[0] = createdPositions.get(0).getId();
        positionIds[1] = createdPositions.get(1).getId();

        createPlayerCommandModel.setPositionIds(positionIds);


        //Act
        Player player = playerWebService.saveCreatePlayerCommandModel(createPlayerCommandModel);


        //Assert
        assert player.getId() != null;
        assert "Pat".equals(player.getFirstName());
        assert "Toner".equals(player.getLastName());
        assert "Virginia".equals(player.getHomeTown());
        assert player.getTeam().getId().equals(createdTeam.getId());

        List<Position> positions = positionService.getPositionsByPlayer(player, Integer.MAX_VALUE, 0);

        boolean savedFirstPosition = false;
        boolean savedSecondPosition = false;

        for (Position position : positions) {
            if (position.getId().equals(createdPositions.get(0).getId())) savedFirstPosition = true;
            if (position.getId().equals(createdPositions.get(1).getId())) savedSecondPosition = true;
        }

        assert savedFirstPosition == true;
        assert savedSecondPosition == true;




    }


    @Test
    public void saveEditPlayerCommandModel() {

        //Arrange
        Team createdTeam = createTestTeam();
        List<Position> createdPositions = createTestPositions(2);


        //Set up test player with a team and a couple existing positions
        Player existingPlayer = createTestPlayerWithTeamAndPositions(createdTeam, createdPositions);


        //In the web side of things spring is going to handle this part for us.
        EditPlayerCommandModel editPlayerCommandModel = new EditPlayerCommandModel();
        editPlayerCommandModel.setId(existingPlayer.getId());
        editPlayerCommandModel.setFirst("John");
        editPlayerCommandModel.setLast("Tally");
        editPlayerCommandModel.setHometown("Taco");


        //Put on different team
        Team updateTeam = createTestTeam();
        editPlayerCommandModel.setTeamId(updateTeam.getId());

        //Play different positions
        List<Position> updatedPositions = createSecondTestPositions(3);


        Long[] positionIds = new Long[3];
        positionIds[0] = updatedPositions.get(0).getId();
        positionIds[1] = updatedPositions.get(1).getId();
        positionIds[2] = updatedPositions.get(2).getId();

        editPlayerCommandModel.setPositionIds(positionIds);


        //Act
        Player editedPlayer = playerWebService.saveEditPlayerCommandModel(editPlayerCommandModel);


        //Assert
        assert editedPlayer.getId() != null;
        assert "John".equals(editedPlayer.getFirstName());
        assert "Tally".equals(editedPlayer.getLastName());
        assert "Taco".equals(editedPlayer.getHomeTown());
        assert editedPlayer.getTeam().getId().equals(updateTeam.getId());

        List<Position> positions = positionService.getPositionsByPlayer(editedPlayer, Integer.MAX_VALUE, 0);

        boolean savedFirstPosition = false;
        boolean savedSecondPosition = false;
        boolean savedThirdPosition = false;
        boolean deletedFirstExistingPosition = true;
        boolean deletedSecondExistingPosition = true;

        for (Position position : positions) {
            //Verify we have the new positions
            if (position.getId().equals(updatedPositions.get(0).getId())) savedFirstPosition = true;
            if (position.getId().equals(updatedPositions.get(1).getId())) savedSecondPosition = true;
            if (position.getId().equals(updatedPositions.get(2).getId())) savedThirdPosition = true;

            //Verify we no longer have the old positions
            if (position.getId().equals(createdPositions.get(0).getId())) deletedFirstExistingPosition = false;
            if (position.getId().equals(createdPositions.get(1).getId())) deletedSecondExistingPosition = false;

        }

        assert savedFirstPosition == true;
        assert savedSecondPosition == true;
        assert savedThirdPosition == true;
        assert deletedFirstExistingPosition == true;
        assert deletedSecondExistingPosition == true;



    }

    private Player createTestPlayerWithTeamAndPositions(Team createdTeam, List<Position> createdPositions) {
        Player existingPlayer = createTestPlayer(createdTeam);

        for (Position position : createdPositions) {
            PlayerPosition playerPosition = new PlayerPosition();
            playerPosition.setPlayer(existingPlayer);
            playerPosition.setPosition(position);
            playerPositionService.create(playerPosition);
        }
        return existingPlayer;
    }

    private Player createTestPlayer(Team createdTeam) {
        Player player = new Player();
        player.setFirstName("Pat");
        player.setLastName("toner");
        player.setHomeTown("Colorado");
        player.setTeam(createdTeam);


        return playerService.create(player);
    }


    @Test
    public void getEditPlayerViewModel() {

        //Arrange
        List<Position> positions = createTestPositions(15);
        List<Team> teams = createTestTeams(15);

        Team createdTeam = teams.get(0);

        List<Position> selectedPositions = new ArrayList<>();
        selectedPositions.add(positions.get(0));
        selectedPositions.add(positions.get(1));


        Player existingPlayer = createTestPlayerWithTeamAndPositions(createdTeam, selectedPositions);



        //Act
        EditPlayerViewModel editPlayerViewModel = playerWebService.getEditPlayerViewModel(existingPlayer.getId());



        //Assert
        assert editPlayerViewModel.getPositions().size() == positions.size();
        assert editPlayerViewModel.getTeams().size() == teams.size();

        for (EditPositionViewModel editPositionViewModel : editPlayerViewModel.getPositions()) {
            assert  editPositionViewModel.getId() != null;
            assert editPositionViewModel.getName() != null;
        }

        for (EditTeamViewModel editTeamViewModel : editPlayerViewModel.getTeams()) {
            assert editTeamViewModel.getId() != null;
            assert editTeamViewModel.getName() != null;
        }

        EditPlayerCommandModel commandModel = editPlayerViewModel.getEditPlayerCommandModel();

        assert commandModel.getId().equals(existingPlayer.getId());
        assert commandModel.getFirst().equals(existingPlayer.getFirstName());
        assert commandModel.getLast().equals(existingPlayer.getLastName());
        assert commandModel.getHometown().equals(existingPlayer.getHomeTown());

        assert commandModel.getTeamId().equals(createdTeam.getId());


        boolean containsFirstPosition = false;
        boolean containsSecondPosition = false;

        for (Long positionId : commandModel.getPositionIds()) {
            if (positionId.equals(positions.get(0).getId())) containsFirstPosition = true;
            if (positionId.equals(positions.get(1).getId())) containsSecondPosition = true;
        }

        assert containsFirstPosition == true;
        assert containsSecondPosition == true;


    }



    private Team createTestTeam() {
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        return teamService.create(team);
    }


    private List<Player> createTestPlayers(int numberOfPlayers) {

        List<Player> players = new ArrayList<>();

        for(int i=0; i < numberOfPlayers; i++) {
            Player player = new Player();

            player.setFirstName("Pat"+i);
            player.setLastName("Toner"+i);
            player.setHomeTown("Arizona"+i);


            Team team = new Team();
            team.setCity("Pittsburgh"+i);
            team.setNickname("Pirates"+i);

            Team createdTeam = teamService.create(team);

            player.setTeam(createdTeam);

            players.add(playerService.create(player));

        }

        return players;
    }

    private List<Team> createTestTeams(int numberOfTeams) {

        List<Team> teamList = new ArrayList<>();

        for (int i=0; i < numberOfTeams; i++) {
            Team team = new Team();
            team.setCity("Pittsburgh"+i);
            team.setNickname("Pirates"+i);

            Team createdTeam = teamService.create(team);
            teamList.add(createdTeam);
        }

        return teamList;
    }

    private List<Position> createTestPositions(int numberOfPositions) {

        List<Position> positionList = new ArrayList<>();

        for (int i=0; i < numberOfPositions; i++) {

            Position position = new Position();
            position.setName("P"+i);
            positionList.add(positionService.create(position));
        }

        return positionList;
    }

    private List<Position> createSecondTestPositions(int numberOfPositions) {

        List<Position> positionList = new ArrayList<>();

        for (int i=0; i < numberOfPositions; i++) {

            Position position = new Position();
            position.setName("1B"+i);
            positionList.add(positionService.create(position));
        }

        return positionList;
    }


}
