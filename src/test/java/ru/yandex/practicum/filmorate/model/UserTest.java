package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.service.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    private final InMemoryUserStorage cont = new InMemoryUserStorage();
    private  final UserServiceImpl cont2 = new UserServiceImpl(cont, filmStorage);
    private final UserController controller = new UserController(cont2);
    private User user;

    @BeforeEach
    public void createUser() {
        user = new User();
        user.setName("Mr Black");
        user.setEmail("black333@mail.ru");
        user.setLogin("black");
        user.setBirthday(LocalDate.of(1999, 11, 12));
    }

    @Test
    public void testCreateUser() {
        Optional<User> userOptional = Optional.ofNullable(controller.addNew(user));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", (int) 1)
                );
    }

    @Test
    public void testFindAllUsers() {
        controller.addNew(user);
        user.setId(1);
        List<User> users = controller.returnAll();
        List<User> users2 = new ArrayList<>();
        users2.add(user);
        assertTrue(users.equals(users2), "The lists with is not equal");
    }
}