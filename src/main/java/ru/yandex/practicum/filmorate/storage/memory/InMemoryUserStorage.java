package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UpdateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.storageinterface.UserStorage;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int idGenerator = 1;

    @Override
    public User addNew(User user) {
        log.info("Request POST /users received");
        user.setId(idGenerator++);
        users.put(user.getId(), user);
        log.info("A new user has been added");
        return user;
    }

    @Override
    public User update(User user) {
        log.info("Request PUT /users");
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User was updated");
        } else throw new UpdateException("User wasn't updated");
        return user;
    }

    @Override
    public List<User> returnAll() {
        log.info("Request GET /users received");
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User remove(Integer id) {
        User removeUser = users.get(id);
        users.remove(id);
        return removeUser;
    }

}
