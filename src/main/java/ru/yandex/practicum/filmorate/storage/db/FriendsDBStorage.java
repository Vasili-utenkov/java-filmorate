package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

import java.util.List;

@Slf4j
@Repository
public class FriendsDBStorage extends BaseRepository<Friend> implements FriendsStorage {

    // Удаление сета с друзьями для userId
    private static final String DELETE_FRIEND_SET_QUERY
            = "DELETE FROM user_friends WHERE user_id = ?";
    // Удаление userId из всех сетов друзей всех пользователей
    private static final String DELETE_FROM_FRIENDS_SET_QUERY
            = "DELETE FROM user_friends WHERE side_two = ?";
    //     добавление в друзья (взаимное)
    private static final String ADD_FRIEND_QUERY
            = "INSERT INTO user_friends (side_one, side_two, confirmed) VALUES (?, ?, ?)";
    //     удаление из друзей (взаимное)
    private static final String REMOVE_FRIEND_QUERY
            = "DELETE FROM user_friends WHERE side_one = ? AND side_two = ?";
    // список друзей
    private static final String GET_FRIENDS_QUERY = """
            SELECT * FROM users AS u
            INNER JOIN user_friends AS uf ON u.id = uf.side_one WHERE uf.user_id = ? AND uf.confirmed = true""";
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
    public List<Long> getFriends(Long friendId1) {
        return getIDList(GET_FRIENDS_QUERY, friendId1);
    }

    // вывод списка общих друзей
    @Override
    public List<Long> getCommonFriends(Long friendId1, Long friendId2) {
        return getIDList(GET_COMMON_FRIEND_QUERY, friendId1, friendId2);
    }


}
