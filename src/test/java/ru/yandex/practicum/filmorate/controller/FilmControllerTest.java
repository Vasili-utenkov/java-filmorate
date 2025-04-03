package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

    private FilmValidator validator = new FilmValidator();
    private FilmController controller = new FilmController();

    @Test
    void validateOK() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(100);
        validator.validate(film);
    }


    @Test
    void validateNotOK() {
        Film film = new Film();
        Exception exception;

        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(100);


//        название не может быть пустым;
        film.setName("");
        exception = assertThrows(ValidationException.class, () -> controller.validate(film));
        assertEquals("Film: название не может быть пустым", exception.getMessage());

        film.setName("Name");


        film.setDescription("");
//        максимальная длина описания — 200 символов;
        exception = assertThrows(ValidationException.class, () -> controller.validate(film));
        assertEquals("Film: описание не может быть пустым", exception.getMessage());


        film.setDescription("q".repeat(201));
        exception = assertThrows(ValidationException.class, () -> controller.validate(film));
        assertEquals("Film: максимальная длина описания — 200 символов", exception.getMessage());

        film.setDescription("Description");

//        дата релиза — не раньше 28 декабря 1895 года;
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        exception = assertThrows(ValidationException.class, () -> controller.validate(film));
        assertEquals("Film: дата релиза — не раньше 28 декабря 1895 года;", exception.getMessage());

        film.setReleaseDate(LocalDate.now());

//        продолжительность фильма должна быть положительным числом.
        film.setDuration(0);
        exception = assertThrows(ValidationException.class, () -> controller.validate(film));
        assertEquals("Film: продолжительность фильма должна быть положительным числом", exception.getMessage());


    }

}