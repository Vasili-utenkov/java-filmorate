package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class FriendsController {

    private FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    // Добавление в список друзей
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long friendId1, @PathVariable("friendId") Long friendId2) {
        log.info("Добавление пользователя с id = " + friendId2 + " в друзья к пользователю с id = " + friendId1);
        friendsService.addFriend(friendId1, friendId2);
    }

    // Удаление из списка друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") Long friendId1, @PathVariable("friendId") Long friendId2) {
        log.info("Добавление пользователя с id = " + friendId2 + " из друзей у пользователя с id = " + friendId1);
        friendsService.removeFriend(friendId1, friendId2);
    }

    // Показ списока друзей пользователя
    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") Long friendId) {
        log.info("Список друзей пользователя с id = " + friendId);
        return friendsService.getFriends(friendId);
    }

    // Показ общего списока друзей с другим пользователем
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Long friendId1, @PathVariable("otherId") Long friendId2) {
        log.info("список общих друзей пользователя с id = " + friendId2 + " и пользователя с id = " + friendId1);
        return friendsService.getCommonFriends(friendId1, friendId2);
    }

}
