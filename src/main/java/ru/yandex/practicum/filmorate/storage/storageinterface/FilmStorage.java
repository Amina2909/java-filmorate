package ru.yandex.practicum.filmorate.storage.storageinterface;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film addNew(Film film);

    Film update(Film film);

    List<Film> returnAll();

    Optional<Film> getById(Integer id);

    Film remove(Integer id);
}
