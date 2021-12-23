package ru.ivmiit.repositories;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;

import java.util.List;
import java.util.Optional;

public interface EventsRepository {
    public List<Event> findAll();
    public Optional<Event> findById(Integer id);
    public List<Event> findByTitle(String title);

    public Event addEvent(Event event);
    public void updateEvent(Event event);
    public void addUserToEvent(User user, Event event);
    public void deleteUserFromEvent(User user, Event event);

    public void deleteEvent(Event event);

    public List<User> getAllEventUsers(Event event);

    public List<Event> getAllEventsWhereAdmin(User user);
    public List<Event> getAllEventsWhereParticipant(User user);
}
