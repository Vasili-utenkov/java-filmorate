package ru.yandex.practicum.filmorate.service.inmemory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendsService;
import ru.yandex.practicum.filmorate.storage.inmemory.FriendsIMStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.UserIMStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service("friendsIMService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "memory", matchIfMissing = true)
public class FriendsIMService implements FriendsService {

    private UserIMStorage userStorage;
    private FriendsIMStorage friendsStorage;

    public FriendsIMService(UserIMStorage userStorage, FriendsIMStorage friendsIMStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsIMStorage;
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
        friendsStorage.addFriend(friendId1, friendId2);
    }

    //     удаление из друзей
    @Override
    public void removeFriend(Long friendId1, Long friendId2) {
        friendsStorage.removeFriend(friendId1, friendId2);
    }

    //     список друзей
    @Override
    public List<User> getFriends(Long friendId1) {
        List<Long> listId = friendsStorage.getFriends(friendId1);

        List<User> list = listId.stream()
                .map(id -> userStorage.getById(id))
                .collect(Collectors.toList());
        return list;
    }

    //     вывод списка общих друзей
    @Override
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
