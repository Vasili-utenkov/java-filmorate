package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendsService;
import ru.yandex.practicum.filmorate.storage.db.FriendsDBStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDBStorage;

import java.util.List;

@Slf4j
@Service("friendsDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class FriendsDBService implements FriendsService {

    private UserDBStorage userStorage;
    private FriendsDBStorage friendsStorage;

    public FriendsDBService(UserDBStorage userStorage, FriendsDBStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    // при удаление пользователя  + пройтись по всем мапам, удалить id
    @Override
    public void deleteFriendsSet(Long userId) {
        userStorage.delete(userId);
        friendsStorage.deleteFromFriendsSet(userId);
    }

    //     добавление в друзья (взаимное)
    @Override
    public void addFriend(Long friendId1, Long friendId2) {
        userStorage.checkNullId(friendId1);
        userStorage.checkNullId(friendId2);
        friendsStorage.addFriend(friendId2, friendId1);

    }

    //     удаление из друзей
    @Override
    public void removeFriend(Long friendId1, Long friendId2) {
        userStorage.checkNullId(friendId1);
        userStorage.checkNullId(friendId2);
        friendsStorage.removeFriend(friendId1, friendId2);
    }

    //     список друзей
    @Override
    public List<User> getFriends(Long friendId1) {
        userStorage.checkNullId(friendId1);
        return friendsStorage.getFriends(friendId1);
    }

    // вывод списка общих друзей
    @Override
    public List<User> getCommonFriends(Long friendId1, Long friendId2) {
        userStorage.checkNullId(friendId1);
        userStorage.checkNullId(friendId2);
        return friendsStorage.getCommonFriends(friendId1, friendId2);
    }
}
