package ru.yandex.practicum.filmorate.storage.inmemory;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FriendsIMStorage implements FriendsStorage {
    private final Map<Long, Set<Long>> friends = new HashMap<>(); // ключ - id пользователя, значение - множество id пользователей, которые друзья

    // Создание нового сета (insert) для userId
    @Override
    public void createEmptyFriendsSet(Long userId) {
        friends.put(userId, new HashSet<>());
    }

    // Удаление сета с друзьями для userId
    @Override
    public void deleteFriendsSet(Long userId) {
        friends.remove(userId);
        deleteFromFriendsSet(userId);
    }

    // Удаление userId из всех сетов друзей всех пользователей
    @Override
    public void deleteFromFriendsSet(Long userId) {
        for (Set<Long> friendSet : friends.values()) {
            friendSet.remove(userId);
        }
    }


    //     добавление в друзья (взаимное)
    @Override
    public void addFriend(Long friendId1, Long friendId2) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        checkFriendContains(friendId2);

        friends.get(friendId1).add(friendId2);
        friends.get(friendId2).add(friendId1);
    }

    //     удаление из друзей (взаимное)
    @Override
    public void removeFriend(Long friendId1, Long friendId2) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        checkFriendContains(friendId2);

        friends.get(friendId1).remove(friendId2);
        friends.get(friendId2).remove(friendId1);
    }

    // список друзей
    @Override
    public List<Long> getFriends(Long friendId1) {
        // + проверка по наличию ключа
        checkFriendContains(friendId1);
        List<Long> list = new ArrayList<>(friends.get(friendId1));
        return list;
    }

    //     вывод списка общих друзей
    @Override
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
