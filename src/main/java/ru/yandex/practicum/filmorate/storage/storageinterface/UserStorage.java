package ru.yandex.practicum.filmorate.storage.storageinterface;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User addNew(User user);

    User update(User user);

    List<User> returnAll();

    Optional<User> getById(Integer id);

    User remove(Integer id);
}
