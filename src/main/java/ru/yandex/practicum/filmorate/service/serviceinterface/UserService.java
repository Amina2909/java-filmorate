package ru.yandex.practicum.filmorate.service.serviceinterface;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addNew(User user);

    User update(User user);

    List<User> returnAll();

    User addFriend(Integer userId, Integer friendId);

    User removeFriend(Integer userId, Integer friendId);

    List<Optional<User>> getCommonFriends(Integer userId, Integer otherId);

    User remove(Integer id);

    User getById(Integer id);

    List<User> getAllFriends(Integer userId);
}
