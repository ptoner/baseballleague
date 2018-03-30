package com.sg.service;

import com.sg.dto.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-applicationContext.xml"})
@Rollback
@Transactional
public class TeamServiceImplTest {

    @Inject
    TeamService teamService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        //Act
        Team createdTeam = teamService.create(team);


        //Assert
        assert createdTeam.getId() != null;
        assert "Pittsburgh".equals(createdTeam.getCity());
        assert "Pirates".equals(createdTeam.getNickname());

    }

    @Test
    public void read() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");
        Team createdTeam = teamService.create(team);


        //Act
        Team readTeam = teamService.read(createdTeam.getId());


        //Assert
        assert "Pittsburgh".equals(readTeam.getCity());
        assert "Pirates".equals(readTeam.getNickname());


    }

    @Test
    public void update() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");
        Team createdTeam = teamService.create(team);

        Team readTeam = teamService.read(createdTeam.getId());

        readTeam.setNickname("The Red Team");
        readTeam.setCity("Boston");


        //Act
        teamService.update(readTeam);


        //Assert
        Team updateTeam = teamService.read(readTeam.getId());

        assert "The Red Team".equals(updateTeam.getNickname());
        assert "Boston".equals(updateTeam.getCity());


    }

    @Test
    public void delete() {

        //Arrange
        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");
        Team createdTeam = teamService.create(team);

        assert createdTeam.getId() != null;

        //Act
        teamService.delete(createdTeam);


        //Assert
        Team readTeam = teamService.read(createdTeam.getId());

        assert readTeam == null;

    }
}