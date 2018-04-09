package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.Position;
import com.sg.dto.Team;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {

    JdbcTemplate jdbcTemplate;

    static String CREATE_QUERY = "INSERT INTO player (first_name, last_name, home_town, team_id) VALUES (?,?,?,?)";
    static String READ_QUERY = "SELECT * FROM player where id = ?";
    static String UPDATE_QUERY = "UPDATE player SET first_name = ?, last_name = ?, home_town = ?, team_id =? WHERE id = ?";
    static String DELETE_QUERY = "DELETE from player WHERE id = ?";
    static String GET_PLAYERS_BY_TEAM_QUERY = "select * from player where team_id = ? LIMIT ? OFFSET ?";
    static String GET_PLAYERS_BY_POSITION_QUERY = "select * from player p " +
                                                  "inner join player_position pp on p.id = pp.player_id " +
                                                  "where pp.position_id = ? LIMIT ? OFFSET ?";
    static String LIST_QUERY = "SELECT * from player LIMIT ? OFFSET ?";

    @Inject
    public PlayerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Player create(Player player) {

        Long teamId = null;

        if (player.getTeam() != null) {
            teamId = player.getTeam().getId();
        }


        jdbcTemplate.update(CREATE_QUERY,
                            player.getFirstName(),
                            player.getLastName(),
                            player.getHomeTown(),
                            teamId
        );

        long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        player.setId(createdId);

        return player;
    }

    @Override
    public Player read(Long id) {

        try {
            Player player = jdbcTemplate.queryForObject(READ_QUERY, new PlayerMapper(), id);
            return player;
        } catch (EmptyResultDataAccessException ex) {}

        return null;

    }

    @Override
    public void update(Player player) {

        Long teamId = null;

        if (player.getTeam() != null) {
            teamId = player.getTeam().getId();
        }

        jdbcTemplate.update(UPDATE_QUERY,
                            player.getFirstName(),
                            player.getLastName(),
                            player.getHomeTown(),
                            teamId,
                            player.getId()
        );
    }

    @Override
    public void delete(Player player) {
        jdbcTemplate.update(DELETE_QUERY, player.getId());
    }

    @Override
    public List<Player> list(int limit, int offset) {
        return jdbcTemplate.query(LIST_QUERY, new PlayerMapper(), limit, offset);
    }


    @Override
    public List<Player> getPlayersByTeam(Team team, int limit, int offset) {

        List<Player> players = jdbcTemplate.query(GET_PLAYERS_BY_TEAM_QUERY,
                new PlayerMapper(),
                team.getId(),
                limit,
                offset
        );


        return players;
    }


    @Override
    public List<Player> getPlayersByPosition(Position position, int limit, int offset) {
        return jdbcTemplate.query(GET_PLAYERS_BY_POSITION_QUERY,
                                  new PlayerMapper(),
                                  position.getId(),
                                  limit,
                                  offset
        );
    }


    private class PlayerMapper implements RowMapper<Player> {

        @Override
        public Player mapRow(ResultSet resultSet, int i) throws SQLException {

            Player player = new Player();

            player.setId(resultSet.getLong("id"));
            player.setFirstName(resultSet.getString("first_name"));
            player.setLastName(resultSet.getString("last_name"));
            player.setHomeTown(resultSet.getString("home_town"));

            Long teamId = resultSet.getLong("team_id");

            if (teamId != null) {

                Team team = new Team();
                team.setId(teamId);

                player.setTeam(team);
            }



            return player;
        }
    }















}
