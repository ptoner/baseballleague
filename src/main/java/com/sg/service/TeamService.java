package com.sg.service;

import com.sg.dto.Team;

import java.util.List;

public interface TeamService {

    public Team create(Team team);
    public Team read(Long id);
    public void update(Team team);
    public void delete(Team team);
    public List<Team> list(int limit, int offset);

}
