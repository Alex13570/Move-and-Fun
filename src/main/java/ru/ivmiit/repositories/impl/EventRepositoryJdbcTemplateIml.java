package ru.ivmiit.repositories.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;
import ru.ivmiit.repositories.EventsRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class EventRepositoryJdbcTemplateIml implements EventsRepository {

    JdbcTemplate jdbcTemplate;

    //Language=SQL
    private String SQL_SELECT_ALL = "select * from events";
    //Language=SQL
    private String SQL_SELECT_BY_ID = "select * from events where id = ?";
    //Language=SQL
    private String SQL_SELECT_TITLE = "select * from events where title like ?";
    //Language=SQL
    private String SQL_INSERT = "insert into events(title,description,event_date,admin_id) values(?, ?, ?, ?)";
    //Language=SQL
    private String SQL_UPDATE = "update events set title = ?, description = ?, event_date = ?, admin_id = ? where id = ?";
    //Language=SQL
    private String SQL_ADD_USER_TO_EVENT = "insert into users_to_events(user_id, event_id) values(?,?)";
    //Language=SQL
    private String SQL_DELETE_USER_FROM_EVENT = "delete from users_to_events where user_id = ? and event_id = ?";
    //Language=SQL
    private String SQL_DELETE_EVENT = "delete from events where id = ?";
    //Language=SQL
    private String SQL_LIST_EVENTS_FROM_USER = "select * from users_to_events ute inner join events e on ute.event_id = e.id where ute.user_id = ?";
    //Language=SQL
    private String SQL_SELECT_WHERE_ADMIN = "select * from events where admin_id = ?";
    //Language=SQL
    private String SQL_LIST_USERS_FROM_EVENT = "select * from users_to_events ute inner join user u on ute.user_id = u.id where ute.event_id = ?";


    public EventRepositoryJdbcTemplateIml(DataSource dataSource){
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
    public List<Event> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, eventRowMapper);
    }

    @Override
    public Optional<Event> findById(Integer id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, eventRowMapper, id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Event> findByTitle(String title) {
        return jdbcTemplate.query(SQL_SELECT_TITLE, eventRowMapper, "%"+title+"%");
    }

    @Override
    public Event addEvent(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});

            statement.setString(1,event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getDate());
            statement.setInt(4, event.getAdminId());

            return statement;
        }, keyHolder);

        event.setId(keyHolder.getKey().intValue());
        return event;
    }

    @Override
    public void updateEvent(Event event) {
        jdbcTemplate.update(SQL_UPDATE, event.getTitle(), event.getDescription(), event.getDate(), event.getAdminId(), event.getId());
    }

    @Override
    public void addUserToEvent(User user, Event event) {
        jdbcTemplate.update(SQL_ADD_USER_TO_EVENT, user.getId(), event.getId());
    }

    @Override
    public void deleteUserFromEvent(User user, Event event) {
        jdbcTemplate.update(SQL_DELETE_USER_FROM_EVENT, user.getId(), event.getId());
    }

    @Override
    public void deleteEvent(Event event) {
        jdbcTemplate.update(SQL_DELETE_EVENT, event.getId());
    }

    @Override
    public List<User> getAllEventUsers(Event event) {
        return jdbcTemplate.query(SQL_LIST_USERS_FROM_EVENT, userRowMapper, event.getId());
    }

    @Override
    public List<Event> getAllEventsWhereAdmin(User user) {
        return jdbcTemplate.query(SQL_SELECT_WHERE_ADMIN, eventRowMapper, user.getId());
    }

    @Override
    public List<Event> getAllEventsWhereParticipant(User user) {
        return jdbcTemplate.query(SQL_LIST_EVENTS_FROM_USER, eventRowMapper, user.getId());
    }
}
