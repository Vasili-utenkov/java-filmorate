package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.AbstaractType;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class AbstractStorage<T extends AbstaractType> implements Storage<T> {
    private final HashMap<Long, T> storage = new HashMap<>();

    @Override
    public T create(T data) {
        checkNullId(data);
        if (storage.containsKey(data.getId())) {
            throw new AlreadyExistException("Существует запись с кодом " + data.getId());
        }
        storage.put(data.getId(), data);
        return data;
    }

    protected void checkNullId(T data) {
        if (data.getId() == null) {
            throw new NotFoundException("Не найден код записи id");
        }
    }

    @Override
    public T update(T data) {
        checkNullId(data);
        storage.replace(data.getId(), data);
        return data;
    }

    @Override
    public void delete(long id) {
        storage.remove(id);
    }

    @Override
    public T getById(long id) {
        final T data = storage.get(id);
        if (data == null) {
            throw new NotFoundException("Не найдена запись по id = " + id);
        }
        return data;
    }

    @Override
    public Collection<T> getAll() {
        return storage.values();
    }
}
