package ru.yandex.practicum.filmorate.storage;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component
public interface UserStorage {
// Добавление пользователя
    User create(User data);
// Ищменение пользователя
    User update(User data);
// Удаление пользователя
    void delete(long id);
// Получение пользователя по коду id
    User getById(long id);
// Получение списка пользователей
    Collection<User> getAll();
}
