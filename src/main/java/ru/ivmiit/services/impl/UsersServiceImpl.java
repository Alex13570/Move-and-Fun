package ru.ivmiit.services.impl;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;
import ru.ivmiit.repositories.UsersRepository;
import ru.ivmiit.services.UsersService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public User register(User user) {
        return usersRepository.addUser(user);
    }

    @Override
    public List<String> getErrors(User user) {
        List<String> result = new LinkedList<>();

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if(!email.contains("@")) result.add("Неверный формат email!");
        if(email.length()>32) result.add("Слишком длинный Email!");
        if(usersRepository.findByEmail(email).isPresent()) result.add("Email занят!");

        if(password.length()<5) result.add("Слишком короткий пароль!");
        if(password.length()>32) result.add("Слишком длинный пароль!");

        if(firstName.length()<1) result.add("Слишком короткое имя!");
        if(firstName.length()>32) result.add("Слишком длинное имя!");

        if(lastName.length()<1) result.add("Слишком короткая фамилия!");
        if(lastName.length()>32) result.add("Слишком длинная фамилия!");

        return result;
    }

    @Override
    public Optional<User> login(User user) {
        Optional<User> optionalUser = usersRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            User currentUser = optionalUser.get();
            if((currentUser.getPassword().equals(user.getPassword()))){
                return Optional.of(currentUser);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        usersRepository.updateUser(user);
    }

    @Override
    public List<Event> getUserEvents(User user) {
        return usersRepository.getAllUserEvents(user);
    }
}
