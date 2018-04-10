package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.Position;
import com.sg.dto.Team;

import java.util.List;

public interface PositionDao {

    public Position create(Position position);
    public Position read(Long id);
    public void update(Position position);
    public void delete(Position position);
    public List<Position> list(int limit, int offset);


    public List<Position> getPositionsByPlayer(Player player, int limit, int offset);

}
