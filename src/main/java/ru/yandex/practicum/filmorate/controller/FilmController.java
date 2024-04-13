package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.serviceinterface.FilmService;


import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private  final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addNew(@Valid @RequestBody Film film) {
        log.info("Request POST /films received");
        Film addFilm = filmService.addNew(film);
        log.info("A new film has been added");
        return addFilm;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Request PUT /films received");
        Film updateFilm = filmService.update(film);
        log.info("Film was updated");
        return updateFilm;
    }

    @GetMapping
    public List<Film> returnAll() {
        log.info("Request GET /films received");
        return filmService.returnAll();
    }

    @GetMapping("/{id}")
    public Optional<Film> getById(@PathVariable Integer id) {
        log.info("Request GET /films/{id} received");
        return filmService.getById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") @Positive Integer count) {
        log.info("Request GET /films/popular received");
        return filmService.getPopular(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Request PUT /films/{id}/like/{userId} received");
        Film film = filmService.addLike(id,userId);
        log.info("Like added!");
        return film;
    }

    @DeleteMapping("/{id}")
    public Film deleteById(@PathVariable Integer id) {
        log.info("Request DELETE /users/{id}");
        Film removeFilm = filmService.remove(id);
        log.info("User was deleted");
        return removeFilm;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Request DELETE /films/{id}/like/{userId} received");
        Film film = filmService.removeLike(id, userId);
        log.info("FLike was deleted");
        return film;

    }

}
