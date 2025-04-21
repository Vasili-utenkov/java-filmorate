package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractStorage<User> {

    private final Map<Long, User> users;
    UserService service;

    public InMemoryUserStorage(UserService service) {
        this.service = service;
        this.users = storage;
    }

    @Override
    public User create(User user) {
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        user.setNameThenNull();
        user.setId(getNextID());
        log.info("Добавили пользователя " + user);
        user = super.create(user);
        log.info("Добавили мапу для пользователя " + user);
        service.addEmptyFriendsSet(user.getId());
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
            return super.update(newUser);
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");

    }

    @Override
    public void delete(long id) {
        log.info("Удалили пользователя с id = " + id);
        service.deleteFriendsSet(id);
        super.delete(id);
    }

    @Override
    public User getById(long id) {
        log.info("Получение пользователя с id = " + id);
        return super.getById(id);
    }

    @Override
    public Collection<User> getAll() {
        log.info("Запрос списка пользователей");
        return super.getAll();
    }

}
