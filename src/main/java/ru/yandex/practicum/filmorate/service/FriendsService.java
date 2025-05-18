package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsService {
    // при удаление пользователя  + пройтись по всем мапам, удалить id
    void deleteFriendsSet(Long userId);

    //     добавление в друзья (взаимное)
    void addFriend(Long friendId1, Long friendId2);

    //     удаление из друзей
    void removeFriend(Long friendId1, Long friendId2);

    //     список друзей для User с кодом friendId1
    List<User> getFriends(Long friendId1);

    //     вывод списка общих друзей для User с кодами friendId1 и friendId2
    List<User> getCommonFriends(Long friendId1, Long friendId2);
}
