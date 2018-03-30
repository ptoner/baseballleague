package com.sg.service;

import com.sg.dto.PlayerPosition;

public interface PlayerPositionService {

    public PlayerPosition create(PlayerPosition playerPosition);
    public PlayerPosition read(Long id);
    public void update(PlayerPosition playerPosition);
    public void delete(PlayerPosition playerPosition);

}
