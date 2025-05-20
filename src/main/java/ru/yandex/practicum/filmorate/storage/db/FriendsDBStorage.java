package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import java.util.List;

@Component
public class FriendsDBStorage extends BaseRepository<User> implements FriendsStorage {

    // Удаление сета с друзьями для userId
    private static final String DELETE_FRIEND_SET_QUERY
            = "DELETE FROM friend WHERE sideOne = ?";
    // Удаление userId из всех сетов друзей всех пользователей
    private static final String DELETE_FROM_FRIENDS_SET_QUERY
            = "DELETE FROM friend WHERE sideTwo = ?";
    //     добавление в друзья (взаимное)
    private static final String ADD_FRIEND_QUERY
            = "INSERT INTO friend (sideOne, sideTwo, isConfirm) VALUES (?, ?, ?)";
    //     удаление из друзей (взаимное)
    private static final String REMOVE_FRIEND_QUERY
            = "DELETE FROM friend WHERE sideOne = ? AND sideTwo = ?";
    // список друзей
    private static final String GET_FRIENDS_QUERY = """
            SELECT * FROM users AS u
            INNER JOIN friend AS uf ON u.id = uf.sideOne WHERE uf.sideOne = ? AND uf.isConfirm = true""";
    //     вывод списка общих друзей
    private static final String GET_COMMON_FRIEND_QUERY = """
            select u.*
            from User u
            join Friend f1 on f1.sideTwo = u.id and f1.sideOne = @user1
            join Friend f2 on f2.sideTwo = u.id and f2.sideOne = @user2
            """;

    public FriendsDBStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    // Создание нового сета (insert) для userId
    @Override
    public void createEmptyFriendsSet(Long userId) {
    }

    // Удаление сета с друзьями для userId
    @Override
    public void deleteFriendsSet(Long userId) {
        delete(DELETE_FRIEND_SET_QUERY, userId);
    }

    // Удаление userId из всех сетов друзей всех пользователей
    @Override
    public void deleteFromFriendsSet(Long userId) {
        delete(DELETE_FROM_FRIENDS_SET_QUERY, userId);
    }

    //     добавление в друзья (взаимное)
    @Override
    public void addFriend(Long friendId1, Long friendId2) {
        insert(ADD_FRIEND_QUERY, false, friendId1, friendId2, true);
        insert(ADD_FRIEND_QUERY, false, friendId2, friendId1, false);
    }

    // удаление из друзей (взаимное)
    @Override
    public void removeFriend(Long friendId1, Long friendId2) {
        delete(REMOVE_FRIEND_QUERY, friendId1, friendId2);
        delete(REMOVE_FRIEND_QUERY, friendId2, friendId1);
    }

    // список друзей
    @Override
    public List<Long> getFriendsID(Long friendId1) {
        return getIDList(GET_FRIENDS_QUERY, friendId1);
    }

    public List<User> getFriends(Long friendId1) {
        return findMany(GET_FRIENDS_QUERY, friendId1);
    }

    // вывод списка общих друзей
    @Override
    public List<Long> getCommonFriendsID(Long friendId1, Long friendId2) {
        return getIDList(GET_COMMON_FRIEND_QUERY, friendId1, friendId2);
    }

    public List<User> getCommonFriends(Long friendId1, Long friendId2) {
        return findMany(GET_COMMON_FRIEND_QUERY, friendId1, friendId2);
    }


}
