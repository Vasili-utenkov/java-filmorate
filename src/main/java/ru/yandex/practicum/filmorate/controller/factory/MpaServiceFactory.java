package ru.yandex.practicum.filmorate.controller.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.Optional;

public class MpaServiceFactory {

    private final Optional<MpaService> imService;
    private final MpaService dbService;
    private final String storageType;

    public MpaServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("genresIMService") Optional<MpaService> imService,
            @Qualifier("genresDBService") MpaService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;
    }

    public MpaService getMPAService() {

        if ("memory".equals(storageType)) {
            return imService.orElseThrow(() ->
                    new IllegalStateException("IM storage requested but FilmDBService is not available"));
        }
        return dbService;
    }
}
