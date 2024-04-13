package ru.yandex.practicum.filmorate.service.serviceinterface;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film addNew(Film film);

    Film update(Film film);

    List<Film> returnAll();

    Optional<Film> getById(Integer id);

    Film remove(Integer id);

    Film addLike(Integer filmId, Integer userId);

    Film removeLike(Integer filmId, Integer userId);

    List<Film> getPopular(Integer count);
}
