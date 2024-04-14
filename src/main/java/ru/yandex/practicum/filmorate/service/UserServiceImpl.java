package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.serviceinterface.UserService;
import ru.yandex.practicum.filmorate.storage.storageinterface.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User addNew(User user) {
        nameEqualsLogin(user);
        return userStorage.addNew(user);
    }

    @Override
    public User update(User user) {
          getById(user.getId());
          nameEqualsLogin(user);
          return userStorage.update(user);
        }

    @Override
    public List<User> returnAll() {
        return userStorage.returnAll();
    }

    @Override
    public User addFriend(Integer userId, Integer friendId) {
            User user = getById(userId);
            User friendUser = getById(friendId);
            user.getFriendsId().add(friendId);
            friendUser.getFriendsId().add(userId);
            userStorage.update(friendUser);
            return userStorage.update(user);
        }

    @Override
    public User removeFriend(Integer userId, Integer friendId) {
        User user = getById(userId);
        User friendUser = getById(friendId);
            user.getFriendsId().remove(friendId);
            friendUser.getFriendsId().remove(userId);
            userStorage.update(user);
            userStorage.update(friendUser);
            return user;
        }

    @Override
    public List<Optional<User>> getCommonFriends(Integer userId, Integer otherId) {
            User user = getById(userId);
            User otherUser = getById(otherId);
            List<Optional<User>> commonFriends = new ArrayList<>();
            for (Integer friendId : user.getFriendsId()) {
                if (otherUser.getFriendsId().contains(friendId)) {
                    commonFriends.add(userStorage.getById(friendId));
                }
            }
            return commonFriends;
        }

    public void nameEqualsLogin(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public User remove(Integer id) {
        return userStorage.remove(id);
    }

    @Override
    public User getById(Integer id) {
        return userStorage.getById(id).orElseThrow(() ->
                new UserNotFoundException("The user was not found"));
    }

    @Override
    public List<User> getAllFriends(Integer userId) {
            return getById(userId).getFriendsId()
                    .stream()
                    .map(this::getById)
                    .collect(Collectors.toList());
        }
}
