package ru.yandex.practicum.filmorate.storage.memory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UpdateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.storageinterface.FilmStorage;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 1;

    @Override
    public Film addNew(Film film) {
        film.setId(idGenerator++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else throw new UpdateException("Film wasn't updated");
        return film;
    }

    @Override
    public List<Film> returnAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> getById(Integer id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film remove(Integer id) {
        Film removeFilm = films.get(id);
        films.remove(id);
        return removeFilm;
    }
}
