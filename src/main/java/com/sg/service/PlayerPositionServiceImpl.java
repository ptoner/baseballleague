package com.sg.service;

import com.sg.dao.PlayerPositionDao;
import com.sg.dto.Player;
import com.sg.dto.PlayerPosition;

import javax.inject.Inject;
import java.util.List;

public class PlayerPositionServiceImpl implements PlayerPositionService {

    PlayerPositionDao playerPositionDao;

    @Inject
    public PlayerPositionServiceImpl(PlayerPositionDao playerPositionDao) {
        this.playerPositionDao = playerPositionDao;
    }


    @Override
    public PlayerPosition create(PlayerPosition playerPosition) {
        return playerPositionDao.create(playerPosition);
    }

    @Override
    public PlayerPosition read(Long id) {
        return playerPositionDao.read(id);
    }

    @Override
    public void update(PlayerPosition playerPosition) {
        playerPositionDao.update(playerPosition);
    }

    @Override
    public void delete(PlayerPosition playerPosition) {
        playerPositionDao.delete(playerPosition);
    }

    @Override
    public List<PlayerPosition> getPlayerPositionByPlayer(Player player, int limit, int offset) {
        return playerPositionDao.getPlayerPositionByPlayer(player, limit, offset);
    }
}
