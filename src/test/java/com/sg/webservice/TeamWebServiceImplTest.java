package com.sg.webservice;

import com.sg.commandmodel.team.createteam.CreateTeamCommandModel;
import com.sg.commandmodel.team.editteam.EditTeamCommandModel;
import com.sg.dto.Team;
import com.sg.service.TeamService;
import com.sg.viewmodel.team.createteam.CreateTeamViewModel;
import com.sg.viewmodel.team.editteam.EditTeamViewModel;
import com.sg.viewmodel.team.teamlist.TeamListViewModel;
import com.sg.viewmodel.team.teamlist.TeamViewModel;
import com.sg.viewmodel.team.teamprofile.TeamProfileViewModel;
import com.sg.webservice.interfaces.TeamWebService;
import org.junit.Before;
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
public class TeamWebServiceImplTest {

    @Inject
    TeamWebService teamWebService;

    @Inject
    TeamService teamService;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void getTeamListViewModel() {

        //Arrange
        List<Team> teams = createTestTeams(15);


        //Act
        TeamListViewModel teamListViewModel = teamWebService.getTeamListViewModel(5, 0, 5);


        //Assert
        assert teamListViewModel.getTeams().size() == 5;

        //Verify we're on the right page number.
        assert teamListViewModel.getPageNumber() == 1;

        //Verify we have the right number of page numbers
        assert teamListViewModel.getPageNumbers().size() == 5;

        //Verify start/end of page numbers are correct
        assert teamListViewModel.getPageNumbers().get(0) == 1;
        assert teamListViewModel.getPageNumbers().get(4) == 5;


        int counter=0;
        for (TeamViewModel teamViewModel : teamListViewModel.getTeams()) {
            assert ("Pittsburgh"+counter).equals(teamViewModel.getCity());
            assert ("Pirates"+counter).equals(teamViewModel.getNickname());
            counter++;
        }



    }

    private List<Team> createTestTeams(int numberOfTeams) {

        List<Team> teams = new ArrayList<>();

        for(int i=0; i < numberOfTeams; i++) {
            Team team = new Team();
            team.setCity("Pittsburgh"+i);
            team.setNickname("Pirates"+i);

            teams.add(teamService.create(team));
        }

        return teams;
    }


    @Test
    public void getTeamProfileViewModel() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        Team createdTeam = teamService.create(team);

        //Act
        TeamProfileViewModel teamProfileViewModel = teamWebService.getTeamProfileViewModel(createdTeam.getId());


        //Assert
        assert teamProfileViewModel.getId().equals(createdTeam.getId());
        assert teamProfileViewModel.getCity().equals(createdTeam.getCity());
        assert teamProfileViewModel.getNickname().equals(createdTeam.getNickname());



    }

    @Test
    public void getCreateTeamViewModel() {

        //Act
        CreateTeamViewModel createTeamViewModel = teamWebService.getCreateTeamViewModel();

        //Assert
        assert  createTeamViewModel != null;

    }

    @Test
    public void getEditTeamViewModel() {

        EditTeamViewModel editTeamViewModel = teamWebService.getEditTeamViewModel();

        //Assert
        assert  editTeamViewModel != null;

    }

    @Test
    public void getEditTeamCommandModel() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        Team createdTeam = teamService.create(team);


        //Act
        EditTeamCommandModel editTeamCommandModel = teamWebService.getEditTeamCommandModel(createdTeam.getId());

        //Assert
        assert editTeamCommandModel.getId().equals(createdTeam.getId());
        assert editTeamCommandModel.getCity().equals(createdTeam.getCity());
        assert editTeamCommandModel.getNickname().equals(createdTeam.getNickname());


    }


    @Test
    public void saveCreateTeamCommandModel() {

        //Arrange
        CreateTeamCommandModel commandModel = new CreateTeamCommandModel();
        commandModel.setCity("Pittsburgh");
        commandModel.setNickname("Pirates");


        //Act
        Team createdTeam = teamWebService.saveCreateTeamCommandModel(commandModel);


        //Assert
        assert createdTeam.getId() != null;
        assert "Pittsburgh".equals(createdTeam.getCity());
        assert "Pirates".equals(createdTeam.getNickname());


    }


    @Test
    public void editTeamCommandModel() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");
        Team createdTeam = teamService.create(team);

//        Team readTeam = teamService.read(createdTeam.getId());

        EditTeamCommandModel commandModel = new EditTeamCommandModel();
        commandModel.setId(createdTeam.getId());
        commandModel.setNickname("The Red Team");
        commandModel.setCity("Boston");


        //Act
        teamWebService.saveEditTeamCommandModel(commandModel);


        //Assert
        Team updateTeam = teamService.read(createdTeam.getId());

        assert "The Red Team".equals(updateTeam.getNickname());
        assert "Boston".equals(updateTeam.getCity());

    }


}
