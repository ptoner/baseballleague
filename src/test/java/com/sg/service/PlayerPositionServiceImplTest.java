package com.sg.service;

import com.sg.dto.Player;
import com.sg.dto.PlayerPosition;
import com.sg.dto.Position;
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
public class PlayerPositionServiceImplTest {

    @Inject
    PlayerPositionService playerPositionService;

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
        PlayerPosition createdPlayerPosition = playerPositionService.create(playerPosition);


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

        PlayerPosition createdPlayerPosition = playerPositionService.create(playerPosition);


        //Act
        PlayerPosition readPlayerPosition = playerPositionService.read(createdPlayerPosition.getId());


        //Assert
        assert readPlayerPosition.getPlayer().getId().equals(player.getId());
        assert readPlayerPosition.getPosition().getId().equals(position.getId());


    }

    @Test
    public void update() {

        //Arrange
        Player player = createTestPlayer();
        Position position = createTestPosition();

        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayer(player);
        playerPosition.setPosition(position);

        PlayerPosition createdPlayerPosition = playerPositionService.create(playerPosition);

        PlayerPosition readPlayerPosition = playerPositionService.read(createdPlayerPosition.getId());


        //Update to different player/position
        Player secondPlayer = createTestPlayer();
        Position secondPosition = createSecondTestPosition();

        readPlayerPosition.setPlayer(secondPlayer);
        readPlayerPosition.setPosition(secondPosition);



        //Act
        playerPositionService.update(readPlayerPosition);


        //Assert
        PlayerPosition updatePlayerPosition = playerPositionService.read(readPlayerPosition.getId());

        assert updatePlayerPosition.getPlayer().getId().equals(secondPlayer.getId());
        assert updatePlayerPosition.getPosition().getId().equals(secondPosition.getId());


    }

    @Test
    public void delete() {

        //Arrange
        Player player = createTestPlayer();
        Position position = createTestPosition();

        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayer(player);
        playerPosition.setPosition(position);

        PlayerPosition createdPlayerPosition = playerPositionService.create(playerPosition);

        //Act
        playerPositionService.delete(createdPlayerPosition);


        //Assert
        PlayerPosition readPlayerPosition = playerPositionService.read(createdPlayerPosition.getId());

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