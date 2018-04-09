package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.Position;
import com.sg.dto.Team;

import java.util.List;

public interface PlayerDao {

    public Player create(Player player);
    public Player read(Long id);
    public void update(Player player);
    public void delete(Player player);
    public List<Player> list(int limit, int offset);



    public List<Player> getPlayersByTeam(Team team, int limit, int offset);

    public List<Player> getPlayersByPosition(Position position, int limit, int offset);

}
