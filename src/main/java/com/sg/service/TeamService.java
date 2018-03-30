package com.sg.service;

import com.sg.dto.Team;

public interface TeamService {

    public Team create(Team team);
    public Team read(Long id);
    public void update(Team team);
    public void delete(Team team);


}
