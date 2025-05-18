package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service("mpaDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class MpaDBService {
}
