package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.PlayerPosition;
import com.sg.dto.Position;
import com.sg.dto.Team;
import com.sg.service.PlayerService;
import com.sg.service.PositionService;
import javafx.geometry.Pos;
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
public class PlayerPositionDaoImplTest {

    @Inject
    PlayerPositionDao playerPositionDao;

    @Inject
    PlayerService playerService;

    @Inject
    PositionService positionService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() {

        //Arrange
        Player player = createTestPlayer();
        Position position = createTestPosition();

        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayer(player);
        playerPosition.setPosition(position);


        //Act
        PlayerPosition createdPlayerPosition = playerPositionDao.create(playerPosition);


        //Assert
        assert createdPlayerPosition.getId() != null;
        assert createdPlayerPosition.getPlayer().getId() == player.getId();
        assert createdPlayerPosition.getPosition().getId() == position.getId();

    }

    @Test
    public void read() {

        //Arrange
        Player player = createTestPlayer();
        Position position = createTestPosition();

        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayer(player);
        playerPosition.setPosition(position);

        PlayerPosition createdPlayerPosition = playerPositionDao.create(playerPosition);


        //Act
        PlayerPosition readPlayerPosition = playerPositionDao.read(createdPlayerPosition.getId());


        //Assert
        assert readPlayerPosition.getPlayer().getId() == player.getId();
        assert readPlayerPosition.getPosition().getId() == position.getId();


    }

    @Test
    public void update() {

        //Arrange
        Player player = createTestPlayer();
        Position position = createTestPosition();

        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayer(player);
        playerPosition.setPosition(position);

        PlayerPosition createdPlayerPosition = playerPositionDao.create(playerPosition);

        PlayerPosition readPlayerPosition = playerPositionDao.read(createdPlayerPosition.getId());


        //Update to different player/position
        Player secondPlayer = createTestPlayer();
        Position secondPosition = createSecondTestPosition();

        readPlayerPosition.setPlayer(secondPlayer);
        readPlayerPosition.setPosition(secondPosition);



        //Act
        playerPositionDao.update(readPlayerPosition);


        //Assert
        PlayerPosition updatePlayerPosition = playerPositionDao.read(readPlayerPosition.getId());

        assert updatePlayerPosition.getPlayer().getId() == secondPlayer.getId();
        assert updatePlayerPosition.getPosition().getId() == secondPosition.getId();


    }

    @Test
    public void delete() {

        //Arrange
        Player player = createTestPlayer();
        Position position = createTestPosition();

        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayer(player);
        playerPosition.setPosition(position);

        PlayerPosition createdPlayerPosition = playerPositionDao.create(playerPosition);

        //Act
        playerPositionDao.delete(createdPlayerPosition);


        //Assert
        PlayerPosition readPlayerPosition = playerPositionDao.read(createdPlayerPosition.getId());

        assert readPlayerPosition == null;

    }


    private Player createTestPlayer() {
        Player player = new Player();
        player.setFirstName("pat");
        player.setLastName("toner");
        return playerService.create(player);
    }

    private Position createTestPosition() {
        Position position = new Position();
        position.setName("P");
        return positionService.create(position);
    }

    private Position createSecondTestPosition() {
        Position position = new Position();
        position.setName("2B");
        return positionService.create(position);
    }


}