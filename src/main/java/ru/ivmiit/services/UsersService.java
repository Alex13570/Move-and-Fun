package ru.ivmiit.services;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    public User register(User user);
    public List<String> getErrors(User user);

    public Optional<User> login(User user);

    public void update(User user);

    public List<Event> getUserEvents(User user);
}
