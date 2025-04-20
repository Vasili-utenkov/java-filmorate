package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private InMemoryUserStorage storage;
    private final Map<Long, Set<Long>> friends = new HashMap<>(); // ключ - id пользователя, значение - множество id пользователей, которые друзья

    @Autowired
    public void setStorage(@Lazy InMemoryUserStorage storage) {
        this.storage = storage;
    }

    // при добавление пользователя
    public void addEmptyFriendsSet(Long userId) {
        friends.put(userId, new HashSet<>());
    }

    // при удаление пользователя  + пройтись по всем мапам, удалить id
    public void deleteFriendsSet(Long userId) {
        friends.remove(userId);
        for (Set<Long> friendSet : friends.values()) {
            friendSet.remove(userId);
        }
    }

    //     добавление в друзья (взаимное)
    public void addFriend(Long friendId1, Long friendId2) {
        friends.get(friendId1).add(friendId2);
        friends.get(friendId2).add(friendId1);
    }

    //     удаление из друзей
    public void removeFriend(Long friendId1, Long friendId2) {
        friends.get(friendId1).remove(friendId2);
        friends.get(friendId2).remove(friendId1);
    }

    //     список друзей
    public List<User> getFriends(Long friendId1) {
        List<User> list = friends.get(friendId1).stream()
                .map(storage::getById)
                .collect(Collectors.toList());
        return list;
    }

    //     вывод списка общих друзей
    public List<User> getCommonFriends(Long friendId1, Long friendId2) {

        return friends.get(friendId1).stream()
                .filter(friends.get(friendId2)::contains)
                .map(storage::getById)
                .collect(Collectors.toList());
    }

}
