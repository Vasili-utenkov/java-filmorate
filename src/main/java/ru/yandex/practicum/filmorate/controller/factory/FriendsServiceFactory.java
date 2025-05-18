package ru.yandex.practicum.filmorate.controller.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.filmorate.service.FriendsService;

public class FriendsServiceFactory {
    private final FriendsService imService;
    private final FriendsService dbService;
    private final String storageType;

    public FriendsServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("friendsIMService") FriendsService imService,
            @Qualifier("friendsDBService") FriendsService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;
    }

    public FriendsService getFriendService() {
        return "db".equals(storageType) ? dbService : imService;
    }
}
