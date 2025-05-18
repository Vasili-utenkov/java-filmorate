package ru.yandex.practicum.filmorate.controller.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.Optional;


public class GenresServiceFactory {
    private final Optional<GenresService> imService;
    private final GenresService dbService;
    private final String storageType;

    public GenresServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("genresIMService") Optional<GenresService> imService,
            @Qualifier("genresDBService") GenresService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;
    }

    public GenresService getGenresService() {

        if ("memory".equals(storageType)) {
            return imService.orElseThrow(() ->
                    new IllegalStateException("IM storage requested but FilmDBService is not available"));
        }
        return dbService;
    }
}
