package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.AbstaractType;
import java.util.Collection;
import java.util.HashMap;

@Component
public class AbstractStorage<T extends AbstaractType> implements Storage<T> {
    private final HashMap<Long, T> storage = new HashMap<>();

    @Override
    public T create(T data) {
        checkNullId(data);
        if (storage.containsKey(data.getId())) {
            throw new AlreadyExistException("Существует запись с кодом " + data.getId());
        }
        return storage.put(data.getId(), data);
    }

    protected void checkNullId(T data) {
        if (data.getId() == null) {
            throw new NotFoundException("Не найден код записи id");
        }
    }

    @Override
    public T update(T data) {
        checkNullId(data);
        return storage.replace(data.getId(), data);
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
