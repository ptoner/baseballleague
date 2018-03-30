package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.Team;
import com.sg.service.TeamService;
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
public class PlayerDaoImplTest {

    @Inject
    PlayerDao playerDao;

    @Inject
    TeamService teamService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() {

        //Arrange
        Player player = new Player();
        player.setFirstName("Pat");
        player.setLastName("Toner");
        player.setHomeTown("Australia");

        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        Team createdTeam = teamService.create(team);

        player.setTeam(createdTeam);


        //Act
        Player createdPlayer = playerDao.create(player);


        //Assert
        assert createdPlayer.getId() != null;
        assert "Pat".equals(createdPlayer.getFirstName());
        assert "Toner".equals(createdPlayer.getLastName());
        assert "Australia".equals(createdPlayer.getHomeTown());
        assert createdPlayer.getTeam().getId() == createdTeam.getId();

    }

    @Test
    public void read() {

        //Arrange
        Player createdPlayer = createTestPlayer();

        //Act
        Player readPlayer = playerDao.read(createdPlayer.getId());


        //Assert
        assert "Pat".equals(readPlayer.getFirstName());
        assert "Toner".equals(readPlayer.getLastName());
        assert "Australia".equals(readPlayer.getHomeTown());
        assert createdPlayer.getTeam().getId() == readPlayer.getTeam().getId();


    }

    private Player createTestPlayer() {
        Player player = new Player();
        player.setFirstName("Pat");
        player.setLastName("Toner");
        player.setHomeTown("Australia");

        Team team = new Team();
        team.setCity("Pittsburgh");
        team.setNickname("Pirates");

        Team createdTeam = teamService.create(team);

        player.setTeam(createdTeam);

        return playerDao.create(player);
    }

    @Test
    public void update() {

        //Arrange
        Player createdPlayer = createTestPlayer();

        Player readPlayer = playerDao.read(createdPlayer.getId());

        //Update fields
        readPlayer.setFirstName("Tom");
        readPlayer.setLastName("Smith");
        readPlayer.setHomeTown("Denver");

        //Move to new team
        Team newTeam = new Team();
        newTeam.setCity("Jacksonville");
        newTeam.setNickname("Falcons");

        Team createdNewTeam = teamService.create(newTeam);

        readPlayer.setTeam(createdNewTeam);

        //Act
        playerDao.update(readPlayer);


        //Assert
        Player updatedPlayer = playerDao.read(readPlayer.getId());

        assert "Tom".equals(updatedPlayer.getFirstName());
        assert "Smith".equals(updatedPlayer.getLastName());
        assert "Denver".equals(updatedPlayer.getHomeTown());
        assert updatedPlayer.getTeam().getId() == createdNewTeam.getId();


    }

    @Test
    public void delete() {

        //Arrange
        Player createdPlayer = createTestPlayer();

        //Act
        playerDao.delete(createdPlayer);


        //Assert
        Player readPlayer = playerDao.read(createdPlayer.getId());

        assert readPlayer == null;

    }
}