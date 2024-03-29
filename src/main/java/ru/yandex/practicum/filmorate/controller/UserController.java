package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Integer, User> users = new HashMap<>();
    private int idGenerator = 1;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addNew(@Valid @RequestBody User user) {
        nameEqualsLogin(user);
        log.info("Request POST /users received");
        user.setId(idGenerator++);
        users.put(user.getId(), user);
        log.info("A new user has been added");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Request PUT /users");
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User was updated");
        } else throw new RuntimeException("User wasn't updated");
        return user;
    }

    @GetMapping
    public List<User> returnAll() {
        log.info("Request GET /users received");
        return new ArrayList<>(users.values());
    }

    public void nameEqualsLogin(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            throw new RuntimeException("The login must not contain gaps");
        }
    }

}

