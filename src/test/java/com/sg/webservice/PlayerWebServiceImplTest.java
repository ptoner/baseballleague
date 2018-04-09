package com.sg.webservice;


import com.sg.dto.Player;
import com.sg.dto.Team;
import com.sg.service.PlayerService;
import com.sg.service.TeamService;
import com.sg.viewmodel.player.playerlist.PlayerListViewModel;
import com.sg.viewmodel.player.playerlist.PlayerViewModel;
import com.sg.viewmodel.team.teamlist.TeamListViewModel;
import com.sg.viewmodel.team.teamlist.TeamViewModel;
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


}
