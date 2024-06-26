package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UpdateException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 1;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addNew(@Valid @RequestBody Film film) {
        log.info("Request POST /films received");
        film.setId(idGenerator++);
        films.put(film.getId(), film);
        log.info("A new film has been added");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Request PUT /films received");
        if (films.containsKey(film.getId())) {
        films.put(film.getId(), film);
        log.info("Film was updated");
        } else throw new UpdateException("Film wasn't updated");
        return film;
    }

    @GetMapping
    public List<Film> returnAll() {
        log.info("Request GET /films received");
        return new ArrayList<>(films.values());
    }


}
