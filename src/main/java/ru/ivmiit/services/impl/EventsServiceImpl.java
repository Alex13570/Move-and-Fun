package ru.ivmiit.services.impl;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;
import ru.ivmiit.repositories.EventsRepository;
import ru.ivmiit.services.EventsService;

import java.util.List;
import java.util.Optional;

public class EventsServiceImpl implements EventsService {

    private EventsRepository eventsRepository;

    public EventsServiceImpl(EventsRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventsRepository.findAll();
    }

    @Override
    public List<Event> listAllWhereParticipant(User user) {
        return eventsRepository.getAllEventsWhereParticipant(user);
    }

    @Override
    public List<Event> listAllWhereAdmin(User user) {
        return eventsRepository.getAllEventsWhereAdmin(user);
    }

    @Override
    public Event addEvent(Event event) {
        return eventsRepository.addEvent(event);
    }

    @Override
    public void updateEvent(Event event) {
        eventsRepository.updateEvent(event);
    }

    @Override
    public void deleteEvent(Event event) {
        eventsRepository.deleteEvent(event);
    }

    @Override
    public void joinEvent(Event event, User user) {
        eventsRepository.addUserToEvent(user, event);
    }

    @Override
    public void leaveEvent(Event event, User user) {
        eventsRepository.deleteUserFromEvent(user, event);
    }

    @Override
    public List<User> listAllEventUsers(Event event) {
        return eventsRepository.getAllEventUsers(event);
    }

    @Override
    public Optional<Event> findById(Integer id) {
        return eventsRepository.findById(id);
    }

    @Override
    public List<Event> findByTitle(String title) {
        return eventsRepository.findByTitle(title);
    }
}
