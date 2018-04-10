package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.Position;
import com.sg.dto.Team;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PositionDaoImpl implements PositionDao {

    JdbcTemplate jdbcTemplate;

    static String CREATE_QUERY = "INSERT INTO position (name) VALUES (?)";
    static String READ_QUERY = "SELECT * FROM position where id = ?";
    static String UPDATE_QUERY = "UPDATE position SET name = ? WHERE id = ?";
    static String DELETE_QUERY = "DELETE from position WHERE id = ?";
    static String GET_POSITIONS_BY_PLAYER_QUERY = "select * from position p " +
                                                  "inner join player_position pp on p.id = pp.position_id " +
                                                  "where pp.player_id = ? LIMIT ? OFFSET ?";
    static String LIST_QUERY = "select * from position LIMIT ? OFFSET ?";

    @Inject
    public PositionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public Position create(Position position) {

        jdbcTemplate.update(CREATE_QUERY,
                            position.getName()
        );

        long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        position.setId(createdId);

        return position;
    }

    @Override
    public Position read(Long id) {

        try {
            Position position = jdbcTemplate.queryForObject(READ_QUERY, new PositionMapper(), id);
            return position;
        } catch (EmptyResultDataAccessException ex) {}

        return null;

    }

    @Override
    public void update(Position position) {

        jdbcTemplate.update(UPDATE_QUERY,
                            position.getName(),
                            position.getId()
        );
    }

    @Override
    public void delete(Position position) {
        jdbcTemplate.update(DELETE_QUERY, position.getId());
    }

    @Override
    public List<Position> list(int limit, int offset) {
        return jdbcTemplate.query(LIST_QUERY, new PositionMapper(), limit, offset);
    }

    @Override
    public List<Position> getPositionsByPlayer(Player player, int limit, int offset) {
        return jdbcTemplate.query(GET_POSITIONS_BY_PLAYER_QUERY,
                                  new PositionMapper(),
                                  player.getId(),
                                  limit,
                                  offset
        );
    }

    private class PositionMapper implements RowMapper<Position> {

        @Override
        public Position mapRow(ResultSet resultSet, int i) throws SQLException {

            Position position = new Position();

            position.setId(resultSet.getLong("id"));
            position.setName(resultSet.getString("name"));

            return position;
        }
    }















}
