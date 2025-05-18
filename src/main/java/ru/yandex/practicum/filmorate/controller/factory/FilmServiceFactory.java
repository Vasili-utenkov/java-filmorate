package ru.yandex.practicum.filmorate.controller.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.FilmService;

@Service
public class FilmServiceFactory {
    private final FilmService imService;
    private final FilmService dbService;
    private final String storageType;

    public FilmServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("filmIMService") FilmService imService,
            @Qualifier("filmDBService") FilmService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;
    }

    public FilmService getFilmService() {
        return "db".equals(storageType) ? dbService : imService;
    }
}