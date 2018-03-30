package com.sg.service;

import com.sg.dao.PositionDao;
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
public class PositionServiceImplTest {

    @Inject
    PositionService positionService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        //Act
        Position createdPosition = positionService.create(position);


        //Assert
        assert createdPosition.getId() != null;
        assert "P".equals(createdPosition.getName());

    }

    @Test
    public void read() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        Position createdPosition = positionService.create(position);

        //Act
        Position readPosition = positionService.read(createdPosition.getId());


        //Assert
        assert "P".equals(readPosition.getName());


    }

    @Test
    public void update() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        Position createdPosition = positionService.create(position);

        Position readPosition = positionService.read(createdPosition.getId());

        readPosition.setName("2B");


        //Act
        positionService.update(readPosition);


        //Assert
        Position updatePosition = positionService.read(readPosition.getId());

        assert "2B".equals(updatePosition.getName());

    }

    @Test
    public void delete() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        Position createdPosition = positionService.create(position);

        assert createdPosition.getId() != null;

        //Act
        positionService.delete(createdPosition);


        //Assert
        Position readPosition = positionService.read(createdPosition.getId());

        assert readPosition == null;

    }
}