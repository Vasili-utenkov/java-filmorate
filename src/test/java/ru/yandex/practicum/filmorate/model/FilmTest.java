package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
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

        if (violations.size() > 0) {
            violations.forEach(violation -> System.out.println(violation.getMessage()));
        }
        if (message.length() > 0) {
            assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(message)));
        }

        if (isOk) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }

    }


    @DisplayName("Положительный тест: общий")
    @Test
    void validateOK() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(200);

        testResult(film, true);
    }

    @DisplayName("Название фильма не может быть пустым")
    @Test
    void blankName() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(200);

        testResult(film, false);
    }


    @DisplayName("Положительный тест: длина описания фильма — 200 символов")
    @Test
    void descriptionLength200() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("q".repeat(20));
        film.setReleaseDate(LocalDate.now());
        film.setDuration(200);

        testResult(film, true);
    }


    @DisplayName("Максимальная длина описания фильма — 200 символов")
    @Test
    void descriptionLength201() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("q".repeat(201));
        film.setReleaseDate(LocalDate.now());
        film.setDuration(200);

        testResult(film, false);
    }

    @DisplayName("Описание фильма не может быть пустым")
    @Test
    void blankDescription() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(200);

        testResult(film, false);
    }


    @DisplayName("Положительный тест: Дата релиза фильма 29 декабря 1895 года")
    @Test
    void releaseDate18951229() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(1895,12,29));
        film.setDuration(200);

        testResult(film, true);
    }


    @DisplayName("Дата релиза фильма не может быть раньше 28 декабря 1895 года")
    @Test
    void releaseDate18951228() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(1895,12,28));
        film.setDuration(200);

        testResult(film, false);
    }

    @DisplayName("Продолжительность фильма должна быть положительным числом")
    @Test
    void negativeDuration() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(-1);

        testResult(film, false);
    }

}