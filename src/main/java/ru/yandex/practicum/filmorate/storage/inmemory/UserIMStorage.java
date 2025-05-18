package ru.yandex.practicum.filmorate.storage.inmemory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserIMStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        user.setNameThenNull();
        user.setId(getNextID());
        log.info("Добавили пользователя " + user);

        checkNullId(user);
        if (users.containsKey(user.getId())) {
            throw new AlreadyExistException("Существует запись с кодом " + user.getId());
        }
        users.put(user.getId(), user);

        return user;
    }

    private long getNextID() {
        long l = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++l;
    }


    @Override
    public User update(User newUser) {
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        newUser.setNameThenNull();

        Long id = newUser.getId();
        if (users.containsKey(id)) {
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            User oldUser = users.get(id);
            users.replace(id, newUser);
            log.info("Изменили данные пользователя" + oldUser);
            checkNullId(newUser);
            users.replace(newUser.getId(), newUser);
            return newUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");

    }

    @Override
    public void delete(long id) {
        log.info("Удалили пользователя с id = " + id);
        users.remove(id);
    }

    @Override
    public User getById(long id) {
        log.info("Получение пользователя с id = " + id);
        final User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("Не найдена запись по id = " + id);
        }
        return user;
    }

    @Override
    public Collection<User> getAll() {
        log.info("Запрос списка пользователей");
        return users.values();
    }

    private void checkNullId(User user) {
        if (user.getId() == null) {
            throw new NotFoundException("Не найден код записи id");
        }
    }


}
