package ru.yandex.practicum.filmorate.controller.factory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.UserService;


@Service
public class UserServiceFactory {

    private final UserService imService;
    private final UserService dbService;
    private final String storageType;

    public UserServiceFactory(
            @Value("${film.storage.type:memory}") String storageType,
            @Qualifier("userIMService") UserService imService,
            @Qualifier("userDBService") UserService dbService
    ) {
        this.storageType = storageType;
        this.imService = imService;
        this.dbService = dbService;
    }

    public UserService getUserService() {
        return "db".equals(storageType) ? dbService : imService;
    }

}
