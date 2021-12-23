package ru.ivmiit.repositories;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    public List<User> findAll();
    public Optional<User> findById(Integer id);
    public Optional<User> findByEmail(String email);
    public List<User> findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
    public List<User> findByFirstLastName(String firstName, String lastName);

    public User addUser(User user);
    public void updateUser(User user);

    public List<Event> getAllUserEvents(User user);
}
