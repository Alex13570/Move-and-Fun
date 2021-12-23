package ru.ivmiit.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;
import ru.ivmiit.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcTemplateImpl implements UsersRepository {

    private JdbcTemplate jdbcTemplate;

    //Language=SQL
    private String SQL_SELECT_ALL = "select * from users";
    //Language=SQL
    private String SQL_SELECT_BY_ID = "select * from users where id = ?";
    //Language=SQL
    private String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    //Language=SQL
    private String SQL_SELECT_BY_FIRST_NAME = "select * from users where first_name = ?";
    //Language=SQL
    private String SQL_SELECT_BY_LAST_NAME = "select * from users where last_name = ?";
    //Language=SQL
    private String SQL_SELECT_BY_FIRST_LAST_NAME = "select * from users where first_name = ? and last_name = ?";
    //Language=SQL
    private String SQL_INSERT = "insert into users(first_name, last_name, email, password, avatar_id) values(?, ?, ?, ?, ?)";
    //Language=SQL
    private String SQL_UPDATE = "update users set first_name = ?, last_name = ?, email = ?, password = ?, avatar_id = ? where id = ?";
    //Language=SQL
    private String SQL_LIST_EVENTS_FROM_USER = "select * from users_to_events ute inner join events e on ute.event_id = e.id where ute.user_id = ?";

    public UserRepositoryJdbcTemplateImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) -> {
        return User.builder()
                .id(row.getInt("id"))
                .firstName(row.getString("first_name"))
                .lastName(row.getString("last_name"))
                .email(row.getString("email"))
                .password(row.getString("password"))
                .avatarId(row.getInt("avatar_id"))
                .build();
    };

    private RowMapper<Event> eventRowMapper = (row, rowNumber) -> {
        return Event.builder()
                .id(row.getInt("id"))
                .description(row.getString("description"))
                .title(row.getString("title"))
                .adminId(row.getInt("admin_id"))
                .date(row.getString("event_date"))
                .build();
    };

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public Optional<User> findById(Integer id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, userRowMapper, email));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        return jdbcTemplate.query(SQL_SELECT_BY_FIRST_NAME, userRowMapper, firstName);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return jdbcTemplate.query(SQL_SELECT_BY_LAST_NAME, userRowMapper, lastName);
    }

    @Override
    public List<User> findByFirstLastName(String firstName, String lastName) {
        return jdbcTemplate.query(SQL_SELECT_BY_FIRST_LAST_NAME, userRowMapper, firstName, lastName);
    }

    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});

            statement.setString(1,user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getAvatarId());

            return statement;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update(SQL_UPDATE, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAvatarId(), user.getId());
    }

    @Override
    public List<Event> getAllUserEvents(User user) {
        return jdbcTemplate.query(SQL_LIST_EVENTS_FROM_USER, eventRowMapper, user.getId());
    }
}
