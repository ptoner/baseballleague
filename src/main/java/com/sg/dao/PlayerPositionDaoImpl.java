package com.sg.dao;

import com.sg.dto.Player;
import com.sg.dto.PlayerPosition;
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

public class PlayerPositionDaoImpl implements PlayerPositionDao {

    JdbcTemplate jdbcTemplate;

    static String CREATE_QUERY = "INSERT INTO player_position (player_id, position_id) VALUES (?,?)";
    static String READ_QUERY = "SELECT * FROM player_position where id = ?";
    static String UPDATE_QUERY = "UPDATE player_position SET player_id = ?, position_id = ? WHERE id = ?";
    static String DELETE_QUERY = "DELETE from player_position WHERE id = ?";
    static String GET_BY_PLAYER = "SELECT * FROM player_position where player_id = ? LIMIT ? OFFSET ?";

    @Inject
    public PlayerPositionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public PlayerPosition create(PlayerPosition playerPosition) {

        Long positionId = null;
        Long playerId = null;

        if (playerPosition.getPlayer() != null) {
            playerId = playerPosition.getPlayer().getId();
        }

        if (playerPosition.getPosition() != null) {
            positionId = playerPosition.getPosition().getId();
        }


        jdbcTemplate.update(CREATE_QUERY,
                playerId,
                positionId
        );

        long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        playerPosition.setId(createdId);

        return playerPosition;
    }

    @Override
    public PlayerPosition read(Long id) {

        try {
            PlayerPosition playerPosition = jdbcTemplate.queryForObject(READ_QUERY, new PlayerPositionMapper(), id);
            return playerPosition;
        } catch (EmptyResultDataAccessException ex) {}

        return null;

    }

    @Override
    public void update(PlayerPosition playerPosition) {

        Long positionId = null;
        Long playerId = null;

        if (playerPosition.getPlayer() != null) {
            playerId = playerPosition.getPlayer().getId();
        }

        if (playerPosition.getPosition() != null) {
            positionId = playerPosition.getPosition().getId();
        }

        jdbcTemplate.update(UPDATE_QUERY,
                playerId,
                positionId,
                playerPosition.getId()
        );
    }

    @Override
    public void delete(PlayerPosition playerPosition) {
        jdbcTemplate.update(DELETE_QUERY, playerPosition.getId());
    }

    @Override
    public List<PlayerPosition> getPlayerPositionByPlayer(Player player, int limit, int offset) {
        return jdbcTemplate.query(GET_BY_PLAYER, new PlayerPositionMapper(), player.getId(), limit, offset);
    }

    private class PlayerPositionMapper implements RowMapper<PlayerPosition> {

        @Override
        public PlayerPosition mapRow(ResultSet resultSet, int i) throws SQLException {

            PlayerPosition playerPosition = new PlayerPosition();

            playerPosition.setId(resultSet.getLong("id"));

            //Lazy load player
            Long playerId = resultSet.getLong("player_id");

            if (playerId != null) {

                Player player = new Player();
                player.setId(playerId);
                playerPosition.setPlayer(player);
            }

            //Lazy load position
            Long positionId = resultSet.getLong("position_id");

            if (positionId != null) {

                Position position = new Position();
                position.setId(positionId);
                playerPosition.setPosition(position);
            }

            return playerPosition;
        }
    }





}
