package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component
public interface UserStorage extends Storage<User> {

    @Override
    User create(User data);

    @Override
    User update(User data);

    @Override
    void delete(long id);

    @Override
    User getById(long id);

    @Override
    Collection<User> getAll();
}
