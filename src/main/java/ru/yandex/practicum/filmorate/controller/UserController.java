package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.factory.FilmServiceFactory;
import ru.yandex.practicum.filmorate.controller.factory.UserServiceFactory;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.inmemory.UserIMService;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceFactory userServiceFactory;
    private UserService filmService = userServiceFactory.getUserService();

    //  получение списка всех пользователей.
    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Запрос списка пользователей");
        return filmService.getAllUsers();
    }

    //    создание пользователя;
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Создание пользователя");
        return filmService.create(user);
    }

    //    обновление пользователя;
    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Изменение пользователя");
        return filmService.update(newUser);
    }


}
