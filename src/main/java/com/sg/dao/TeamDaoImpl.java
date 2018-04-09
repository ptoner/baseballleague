package com.sg.dao;

import com.sg.dto.Team;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeamDaoImpl implements TeamDao {

    JdbcTemplate jdbcTemplate;

    static String CREATE_QUERY = "INSERT INTO team (city, nickname) VALUES (?,?)";
    static String READ_QUERY = "SELECT * FROM team where id = ?";
    static String UPDATE_QUERY = "UPDATE team SET city = ?, nickname = ? WHERE id = ?";
    static String DELETE_QUERY = "DELETE from team WHERE id = ?";
    static String LIST_QUERY = "SELECT * from team LIMIT ? OFFSET ?";

    @Inject
    public TeamDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public Team create(Team team) {

        jdbcTemplate.update(CREATE_QUERY,
                            team.getCity(),
                            team.getNickname()
        );

        long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        team.setId(createdId);

        return team;
    }

    @Override
    public Team read(Long id) {

        try {
            Team team = jdbcTemplate.queryForObject(READ_QUERY, new TeamMapper(), id);
            return team;
        } catch (EmptyResultDataAccessException ex) {}

        return null;

    }

    @Override
    public void update(Team team) {

        jdbcTemplate.update(UPDATE_QUERY,
                            team.getCity(),
                            team.getNickname(),
                            team.getId()
        );
    }

    @Override
    public void delete(Team team) {
        jdbcTemplate.update(DELETE_QUERY, team.getId());
    }

    @Override
    public List<Team> list(int limit, int offset) {
        return jdbcTemplate.query(LIST_QUERY, new TeamMapper(), limit, offset);
    }

    private class TeamMapper implements RowMapper<Team> {

        @Override
        public Team mapRow(ResultSet resultSet, int i) throws SQLException {

            Team team = new Team();

            team.setId(resultSet.getLong("id"));
            team.setCity(resultSet.getString("city"));
            team.setNickname(resultSet.getString("nickname"));

            return team;
        }
    }















}
