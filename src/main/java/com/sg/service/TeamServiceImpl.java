package com.sg.service;

import com.sg.dao.TeamDao;
import com.sg.dto.Team;

import javax.inject.Inject;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    TeamDao teamDao;

    @Inject
    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Team create(Team team) {
        return teamDao.create(team);
    }

    @Override
    public Team read(Long id) {
        return teamDao.read(id);
    }

    @Override
    public void update(Team team) {
        teamDao.update(team);
    }

    @Override
    public void delete(Team team) {
        teamDao.delete(team);
    }

    @Override
    public List<Team> list(int limit, int offset) {
        return teamDao.list(limit, offset);
    }
}
