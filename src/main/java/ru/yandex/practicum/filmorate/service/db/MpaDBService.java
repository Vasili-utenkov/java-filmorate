package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@Service("mpaDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class MpaDBService implements MpaService {
    @Override
    public List<MPA> getAll() {
        return null;
    }

    @Override
    public MPA getByID(Long id) {
        return null;
    }
}
