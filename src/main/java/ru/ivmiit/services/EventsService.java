package ru.ivmiit.services;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;

import java.util.List;
import java.util.Optional;

public interface EventsService {
    public List<Event> listAll();
    public List<Event> listAllWhereParticipant(User user);
    public List<Event> listAllWhereAdmin(User user);

    public Event addEvent(Event event);
    public void updateEvent(Event event);
    public void deleteEvent(Event event);

    public void joinEvent(Event event, User user);
    public void leaveEvent(Event event, User user);

    public List<User> listAllEventUsers(Event event);
    public Optional<Event> findById(Integer id);
    public List<Event> findByTitle(String title);
}
