package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Film film;
    private FilmController controller = new FilmController();


    @BeforeEach
    public void createFilm() {
        film = new Film();
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

    @Test
    public void testUpdateFilm() {
        controller.addNew(film);
        film.setDescription("new description");
        controller.update(film);
        assertEquals(film.getDescription(), "new description");
    }

    @Test
    public void testFindAllFilm() {
        controller.addNew(film);
        List<Film> films = controller.returnAll();
        List<Film> films2 = new ArrayList<>();
        films2.add(film);
        assertTrue(films.equals(films2), "The list is not equal");
    }



}