package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface FriendsStorage {
    // Создание нового сета (insert)
    void createEmptyFriendsSet(Long userId);

    // Удаление сета
    void deleteFriendsSet(Long userId);

    // Удаление кода из всех сетов
    void deleteFromFriendsSet(Long userId);

    //     добавление в друзья (взаимное)
    void addFriend(Long friendId1, Long friendId2);

    //     удаление из друзей (взаимное)
    void removeFriend(Long friendId1, Long friendId2);

    // список друзей
    List<Long> getFriends(Long friendId1);

    List<Long> getCommonFriends(Long friendId1, Long friendId2);
}
