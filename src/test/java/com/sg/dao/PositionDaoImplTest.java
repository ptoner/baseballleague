package com.sg.dao;

import com.sg.dto.Position;
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
public class PositionDaoImplTest {

    @Inject
    PositionDao positionDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        //Act
        Position createdPosition = positionDao.create(position);


        //Assert
        assert createdPosition.getId() != null;
        assert "P".equals(createdPosition.getName());

    }

    @Test
    public void read() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        Position createdPosition = positionDao.create(position);

        //Act
        Position readPosition = positionDao.read(createdPosition.getId());


        //Assert
        assert "P".equals(readPosition.getName());


    }

    @Test
    public void update() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        Position createdPosition = positionDao.create(position);

        Position readPosition = positionDao.read(createdPosition.getId());

        readPosition.setName("2B");


        //Act
        positionDao.update(readPosition);


        //Assert
        Position updatePosition = positionDao.read(readPosition.getId());

        assert "2B".equals(updatePosition.getName());

    }

    @Test
    public void delete() {

        //Arrange
        Position position = new Position();
        position.setName("P");

        Position createdPosition = positionDao.create(position);

        assert createdPosition.getId() != null;

        //Act
        positionDao.delete(createdPosition);


        //Assert
        Position readPosition = positionDao.read(createdPosition.getId());

        assert readPosition == null;

    }
}