package ru.yandex.practicum.filmorate.controller.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.db.FilmDBService;
import ru.yandex.practicum.filmorate.service.inmemory.FilmIMService;


@Slf4j
@Service
public class FilmServiceFactory {
    private final String storageType;
    private final FilmIMService imService;
    private final FilmDBService dbService;

    public FilmServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("filmIMService") FilmIMService imService,
            @Qualifier("filmDBService") FilmDBService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;

        log.info("Initialized FilmServiceFactory with storage type: {}", storageType);
    }

    public FilmService getFilmService() {
        return "db".equals(storageType) ? dbService : imService;
    }
}