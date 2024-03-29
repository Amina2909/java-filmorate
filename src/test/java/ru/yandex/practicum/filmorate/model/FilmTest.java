package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Film film;
    private FilmController controller = new FilmController();


    @BeforeEach
    public void createFilm() {
        film = new Film();
        film.setId(1);
        film.setName("Back to the Future");
        film.setDescription("Genre: Science fiction, Comedy, Adventure");
        film.setReleaseDate(LocalDate.of(1985, Month.JULY, 3));
        film.setDuration(116);

    }

    @Test
    public void testCreateFilm() {
        controller.addNew(film);
        assertThat(film).hasFieldOrPropertyWithValue("id", 1);
    }

}