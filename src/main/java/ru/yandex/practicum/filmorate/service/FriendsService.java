package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsService {

    private UserStorage userStorage;
    private FriendsStorage friendsStorage;

    public FriendsService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    // при удаление пользователя  + пройтись по всем мапам, удалить id
    public void deleteFriendsSet(Long userId) {
        userStorage.delete(userId);
        friendsStorage.deleteFromFriendsSet(userId);
    }

    //     добавление в друзья (взаимное)
    public void addFriend(Long friendId1, Long friendId2) {
        friendsStorage.addFriend(friendId1, friendId2);
    }

    //     удаление из друзей
    public void removeFriend(Long friendId1, Long friendId2) {
        friendsStorage.removeFriend(friendId1, friendId2);
    }

    //     список друзей
    public List<User> getFriends(Long friendId1) {
        List<Long> listId = friendsStorage.getFriends(friendId1);

        List<User> list = listId.stream()
                .map(id -> userStorage.getById(id))
                .collect(Collectors.toList());
        return list;
    }

    //     вывод списка общих друзей
    public List<User> getCommonFriends(Long friendId1, Long friendId2) {
        List<Long> listId1 = friendsStorage.getFriends(friendId1);
        List<Long> listId2 = friendsStorage.getFriends(friendId2);

        List<User> list = listId1.stream()
                .filter(listId2::contains)
                .map(id -> userStorage.getById(id))
                .collect(Collectors.toList());

        return list;
    }


}
