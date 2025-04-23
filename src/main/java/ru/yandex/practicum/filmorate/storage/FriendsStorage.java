package ru.yandex.practicum.filmorate.storage;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FriendsStorage {
    private final Map<Long, Set<Long>> friends = new HashMap<>(); // ключ - id пользователя, значение - множество id пользователей, которые друзья


    // Создание нового сета (insert)
    public void createEmptyFriendsSet(Long userId) {
        friends.put(userId, new HashSet<>());
    }

    // Удаление сета
    public void deleteFriendsSet(Long userId) {
        friends.remove(userId);
        deleteFromFriendsSet(userId);
    }

    // Удаление кода из всех сетов
    public void deleteFromFriendsSet(Long userId) {
        for (Set<Long> friendSet : friends.values()) {
            friendSet.remove(userId);
        }
    }


    //     добавление в друзья (взаимное)
    public void addFriend(Long friendId1, Long friendId2) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        checkFriendContains(friendId2);

        friends.get(friendId1).add(friendId2);
        friends.get(friendId2).add(friendId1);
    }

    //     удаление из друзей (взаимное)
    public void removeFriend(Long friendId1, Long friendId2) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        checkFriendContains(friendId2);

        friends.get(friendId1).remove(friendId2);
        friends.get(friendId2).remove(friendId1);
    }

    // список друзей
    // переделать на коды. пользователей подставить наверху
    public List<Long> getFriends(Long friendId1) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        List<Long> list = new ArrayList<>(friends.get(friendId1));
        return list;
    }

    //     вывод списка общих друзей
    // -- Вывести наверх заполнение кодами пользователя

    public List<Long> getCommonFriends(Long friendId1, Long friendId2) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        checkFriendContains(friendId2);

        Set<Long> friendsForFriend2 = friends.get(friendId2);
        return friends.get(friendId1).stream()
                .filter(friendsForFriend2::contains)
                .collect(Collectors.toList());
    }

    protected void checkFriendContains(Long friendId) {
        if (!friends.containsKey(friendId)) {
            throw new NotFoundException("Переданный пользователь не найден id = " + friendId);
        }
    }

}
