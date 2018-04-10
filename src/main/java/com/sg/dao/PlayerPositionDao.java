package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.PlayerPosition;
import com.sg.dto.Position;

import java.util.List;

public interface PlayerPositionDao {

    public PlayerPosition create(PlayerPosition playerPosition);
    public PlayerPosition read(Long id);
    public void update(PlayerPosition playerPosition);
    public void delete(PlayerPosition playerPosition);
    public List<PlayerPosition> getPlayerPositionByPlayer(Player player, int limit, int offset);

}
