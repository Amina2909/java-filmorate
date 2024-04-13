package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryUserStorage;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class FilmTest {
    private Film film;
    private final InMemoryUserStorage user = new InMemoryUserStorage();
  private final InMemoryFilmStorage cont = new InMemoryFilmStorage();
  private  final FilmServiceImpl cont2 = new FilmServiceImpl(cont, user);
  private final FilmController controller = new FilmController(cont2);


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