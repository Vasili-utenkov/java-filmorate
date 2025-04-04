package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private void testResult(User user, boolean isOk) {
        testResult(user, isOk, "");
    }


    private void testResult(User user, boolean isOk, String message) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<User>> violations = factory.getValidator().validate(user);

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
    public void validateOK() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now());
        testResult(user, true);
    }

    @DisplayName("Электронная почта не может быть пустой")
    @Test
    public void testInvalidEmailEmpty() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login");
        user.setEmail(""); // Пустая строка
        user.setBirthday(LocalDate.now());

        testResult(user, false);
    }

    @DisplayName("Не верный формат для электронной почты")
    @Test
    public void testInvalidEmailNoAt() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login");
        user.setEmail("invalid-email"); // Нет символа @
        user.setBirthday(LocalDate.now());

        testResult(user, false);
    }

    @DisplayName("Логин не может быть пустым")
    @Test
    public void testInvalidLoginEmpty() {
        User user = new User();

        user.setName("User Name");
        user.setLogin(""); // Пустая строка
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now());

        testResult(user, false);
    }

    @DisplayName("Пароль не должен содержать пробелы")
    @Test
    public void testInvalidLoginWithSpace() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login with space"); // Логин с пробелами
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now());

        testResult(user, false);
    }

    @DisplayName("Положительный тест: Пустое имя (допускается)")
    @Test
    public void testValidNameEmpty() {
        User user = new User();

        user.setName(""); // Пустое имя (допускается)
        user.setLogin("login");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now());

        testResult(user, true);
    }

    @DisplayName("Положительный тест: Дата рождения в прошлом")
    @Test
    public void testValidBirthday() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now().minusDays(1)); // Дата рождения в прошлом

        testResult(user, true);
    }

    @DisplayName("Дата рождения не может быть в будущем")
    @Test
    public void testInvalidBirthdayFuture() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now().plusDays(1)); // Дата рождения в будущем

        testResult(user, false);
    }

}