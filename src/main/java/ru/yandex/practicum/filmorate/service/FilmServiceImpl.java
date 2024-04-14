package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.serviceinterface.FilmService;
import ru.yandex.practicum.filmorate.storage.storageinterface.FilmStorage;
import ru.yandex.practicum.filmorate.storage.storageinterface.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Film addNew(Film film) {
        return filmStorage.addNew(film);
    }

    @Override
    public Film update(Film film) {
        getById(film.getId());
        return filmStorage.update(film);
    }

    @Override
    public List<Film> returnAll() {
        return filmStorage.returnAll();
    }

    @Override
    public Optional<Film> getById(Integer id) {
        return Optional.ofNullable(filmStorage.getById(id).orElseThrow(() ->
                new FilmNotFoundException("The film or user was not found")));
    }

    @Override
    public Film remove(Integer id) {
        getById(id);
        return filmStorage.remove(id);
        }

    @Override
    public Film addLike(Integer filmId, Integer userId) {
       getById(filmId);
       userStorage.getById(userId);
            Film film = filmStorage.getById(filmId).get();
            film.getLikes().add(userId);
            return filmStorage.update(film);
        }

    @Override
    public Film removeLike(Integer filmId, Integer userId) {
       getById(filmId);
       userStorage.getById(userId);
       Film film = filmStorage.getById(filmId).get();
            film.getLikes().remove(userId);
            return filmStorage.update(film);
        }

    @Override
    public List<Film> getPopular(Integer count) {
        List<Film> films = filmStorage.returnAll();
        films.sort((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()));
        if (films.size() > count) {
            return films.subList(0, count);
        } else {
            return films;
        }
    }
}
