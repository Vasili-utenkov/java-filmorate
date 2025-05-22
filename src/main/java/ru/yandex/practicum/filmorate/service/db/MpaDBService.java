package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.db.MpaDBStorage;
import java.util.List;

@Slf4j
@Service("mpaDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class MpaDBService implements MpaService {

    private MpaDBStorage mpaDBStorage;

    public MpaDBService(MpaDBStorage mpaDBStorage) {
        this.mpaDBStorage = mpaDBStorage;
    }

    @Override
    public List<MPA> getAll() {
        return mpaDBStorage.getAll();
    }

    @Override
    public MPA getByID(Long id) {
        MPA mpa = mpaDBStorage.getByID(id);
        if (mpa == null) {
            throw new NotFoundException(String.format("MPA с кодом %d не найден", id));
        }
        return mpa;
    }
}
