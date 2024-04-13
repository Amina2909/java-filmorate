package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.serviceinterface.UserService;
import ru.yandex.practicum.filmorate.storage.storageinterface.UserStorage;
import ru.yandex.practicum.filmorate.storage.storageinterface.FilmStorage;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Override
    public User addNew(User user) {
        nameEqualsLogin(user);
        return userStorage.addNew(user);
    }

    @Override
    public User update(User user) {
        if (userStorage.getById(user.getId()).isPresent()) {
            nameEqualsLogin(user);
            return userStorage.update(user);
        } else {
            throw new UserNotFoundException("The user was not found");
        }
    }

    @Override
    public List<User> returnAll() {
        return userStorage.returnAll();
    }

    @Override
    public User addFriend(Integer userId, Integer friendId) {
        if (userStorage.getById(userId).isPresent() && userStorage.getById(friendId).isPresent()) {
            User user = userStorage.getById(userId).get();
            User friendUser = userStorage.getById(friendId).get();
            user.getFriendsId().add(friendId);
            friendUser.getFriendsId().add(userId);
            userStorage.update(friendUser);

            return userStorage.update(user);
        } else {
            throw new UserNotFoundException("User or Film was not found");
        }

    }

    @Override
    public User removeFriend(Integer userId, Integer friendId) {
        Optional<User> userOptional = userStorage.getById(userId);
        Optional<User> friendUserOptional = userStorage.getById(friendId);

        if (userOptional.isPresent() && friendUserOptional.isPresent()) {
            User user = userOptional.get();
            User friendUser = friendUserOptional.get();

            user.getFriendsId().remove(friendId);
            friendUser.getFriendsId().remove(userId);

            userStorage.update(user);
            userStorage.update(friendUser);

            return user;
        } else {
            throw new UserNotFoundException("User or friend was not found");
        }

    }

    @Override
    public List<Optional<User>> getCommonFriends(Integer userId, Integer otherId) {
        Optional<User> userOpt = userStorage.getById(userId);
        Optional<User> otherUserOpt = userStorage.getById(otherId);

        if (userOpt.isPresent() && otherUserOpt.isPresent()) {
            User user = userOpt.get();
            User otherUser = otherUserOpt.get();

            List<Optional<User>> commonFriends = new ArrayList<>();
            for (Integer friendId : user.getFriendsId()) {
                if (otherUser.getFriendsId().contains(friendId)) {
                    commonFriends.add(userStorage.getById(friendId));
                }
            }
            return commonFriends;
        } else {
            throw new UserNotFoundException("User or friend was not found");
        }
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
        if (userStorage.getById(userId).isPresent()) {
            return userStorage.getById(userId).get().getFriendsId()
                    .stream()
                    .map(this::getById)
                    .collect(Collectors.toList());
        } else {
            throw new UserNotFoundException("The user was not found");
        }
    }
}
