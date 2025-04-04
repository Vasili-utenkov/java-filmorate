package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FilmTest {

    private void testResult(Film film, boolean isOk) {
        testResult(film, isOk, "");
    }


    private void testResult(Film film, boolean isOk, String message) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Film>> violations = factory.getValidator().validate(film);
        if (isOk) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
        if (violations.size() > 0) {
            violations.forEach(violation -> System.out.println(violation.getMessage()));
        }
        if (message.length() > 0) {
            assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(message)));
        }
    }

    @Test
    void validateOK() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(100);

        testResult(film, true);
    }


    @Test
    void validateNotOK() {
        Film film = new Film();

        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(100);


//        название не может быть пустым;
        film.setName("");
        testResult(film, false);

        film.setName("Name");


        film.setDescription("");
//        максимальная длина описания — 200 символов;
        testResult(film, false);

        film.setDescription("q".repeat(201));
        testResult(film, false);

        film.setDescription("Description");

//        дата релиза — не раньше 28 декабря 1895 года;
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        testResult(film, false);

        film.setReleaseDate(LocalDate.now());

//        продолжительность фильма должна быть положительным числом.
        film.setDuration(0);
        testResult(film, false);


    }

}