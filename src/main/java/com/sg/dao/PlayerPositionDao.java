package com.sg.dao;

import com.sg.dto.PlayerPosition;
import com.sg.dto.Position;

public interface PlayerPositionDao {

    public PlayerPosition create(PlayerPosition playerPosition);
    public PlayerPosition read(Long id);
    public void update(PlayerPosition playerPosition);
    public void delete(PlayerPosition playerPosition);

}
