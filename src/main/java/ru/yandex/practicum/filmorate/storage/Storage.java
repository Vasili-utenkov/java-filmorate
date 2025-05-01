package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.AbstaractType;

import java.util.Collection;

@Component
public interface Storage<T extends AbstaractType> {

    T create(T data);

    T update(T data);

    void delete(long id);

    T getById(long id);

    Collection<T> getAll();
}
