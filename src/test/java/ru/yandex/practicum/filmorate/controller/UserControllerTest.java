package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserValidator validator = new UserValidator();
    private UserController controller = new UserController();

    @Test
    void validateOK() {
        User user = new User();
        user.setName("User Name");
        user.setLogin("login");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now());
        validator.validate(user);
    }

    @Test
    void validateNotOK() {
        User user = new User();
        Exception exception;

        user.setEmail("user@mail.ru");
        user.setName("User Name");
        user.setLogin("login");
        user.setBirthday(LocalDate.now());

//        электронная почта не может быть пустой и должна содержать символ @;
        user.setEmail("");

        exception = assertThrows(ValidationException.class, () -> controller.validate(user));
        assertEquals("User: электронная почта не может быть пустой", exception.getMessage());

        user.setEmail("email");
        exception = assertThrows(ValidationException.class, () -> controller.validate(user));
        assertEquals("User: электронная почта должна содержать символ @", exception.getMessage());

        user.setEmail("user@mail.ru");
//        логин не может быть пустым и содержать пробелы;

        user.setLogin("");
        exception = assertThrows(ValidationException.class, () -> controller.validate(user));
        assertEquals("User: логин не может быть пустым", exception.getMessage());

        user.setLogin("login ");
        exception = assertThrows(ValidationException.class, () -> controller.validate(user));
        assertEquals("User: логин не может содержать пробелы", exception.getMessage());

        user.setLogin("login");
//        дата рождения не может быть в будущем.
        user.setBirthday(LocalDate.now().plusDays(1));
        exception = assertThrows(ValidationException.class, () -> controller.validate(user));
        assertEquals("User: дата рождения не может быть в будущем", exception.getMessage());

    }

}