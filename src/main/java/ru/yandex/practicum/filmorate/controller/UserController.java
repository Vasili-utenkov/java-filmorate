package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.inmemory.UserIMService;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserIMService userIMService;

    @Autowired
    public UserController(UserIMService userIMService) {
        this.userIMService = userIMService;
    }

    //  получение списка всех пользователей.
    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Запрос списка пользователей");
        return userIMService.getAllUsers();
    }

    //    создание пользователя;
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Создание пользователя");
        return userIMService.create(user);
    }

    //    обновление пользователя;
    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Изменение пользователя");
        return userIMService.update(newUser);
    }


}
