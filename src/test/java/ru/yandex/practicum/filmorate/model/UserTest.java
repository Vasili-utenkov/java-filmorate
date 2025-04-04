package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private void testResult (User user, boolean isOk) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<User>> violations = factory.getValidator().validate(user);
        if (isOk) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
        if (violations.size() > 0) {
            violations.forEach(violation -> System.out.println(violation.getMessage()));
        }
//        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("Логин не может содержать пробелы")));
    }

    @Test
    public void validateOK() {
        User user = new User();

        user.setName("User Name");
        user.setLogin("login");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now());
        testResult(user, true);
    }

    @Test
    public void testInvalidEmailEmpty() {
        User user = new User();
        user.setEmail(""); // Пустая строка
        user.setLogin("validLogin");
        user.setName("Valid Name");

        testResult(user, false);
    }

    @Test
    public void testInvalidEmailNoAt() {
        User user = new User();
        user.setEmail("invalid-email"); // Нет символа @
        user.setLogin("validLogin");
        user.setName("Valid Name");

        testResult(user, false);
    }

    @Test
    public void testInvalidLoginEmpty() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setLogin(""); // Пустая строка
        user.setName("Valid Name");

        testResult(user, false);
    }

    @Test
    public void testInvalidLoginWithSpace() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setLogin("login with space"); // Логин с пробелами
        user.setName("Valid Name");

        testResult(user, false);
    }

    @Test
    public void testValidNameEmpty() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setLogin("validLogin");
        user.setName(""); // Пустое имя (допускается)

        testResult(user, true);
    }

    @Test
    public void testValidBirthday() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setLogin("validLogin");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.now().minusDays(1)); // Дата рождения в прошлом

        testResult(user, true);
    }

    @Test
    public void testInvalidBirthdayFuture() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setLogin("validLogin");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.now().plusDays(1)); // Дата рождения в будущем

        testResult(user, false);
    }

}