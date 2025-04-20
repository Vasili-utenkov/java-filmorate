package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    InMemoryUserStorage storage;

    @Autowired
    private UserService service;

    //    получение списка всех пользователей.
    @GetMapping
    public Collection<User> getUsers() {
        return storage.getAll();
    }

    //    создание пользователя;
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return storage.create(user);
    }

    //    обновление пользователя;
    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        return storage.update(newUser);
    }

//    PUT /users/{id}/friends/{friendId} — добавление в друзья.
//            DELETE /users/{id}/friends/{friendId} — удаление из друзей.
//            GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
//            GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.


    // Добавление друга
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long friendId1, @PathVariable("friendId") Long friendId2) {
        service.addFriend(friendId1, friendId2);
    }

    // Удаление друга
    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") Long friendId1, @PathVariable("friendId") Long friendId2) {
        service.removeFriend(friendId1, friendId2);
    }

    // Список друзей пользователя
    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") Long friendId) {
        return service.getFriends(friendId);
    }

    // Общий список друзей с другим пользователем
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Long friendId1, @PathVariable("friendId") Long friendId2) {
        return service.getCommonFriends(friendId1, friendId2);
    }

}
