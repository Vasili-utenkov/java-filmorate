package ru.yandex.practicum.filmorate.controller.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.LikeService;

@Service
public class LikeServiceFactory {

    private final LikeService imService;
    private final LikeService dbService;
    private final String storageType;

    public LikeServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("likeIMService") LikeService imService,
            @Qualifier("likeDBService") LikeService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;
    }

    public LikeService getLikeService() {
        return "db".equals(storageType) ? dbService : imService;
    }
}
