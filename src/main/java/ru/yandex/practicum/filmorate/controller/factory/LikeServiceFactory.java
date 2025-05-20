package ru.yandex.practicum.filmorate.controller.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.LikeService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceFactory {

    private final Environment environment;
    private final ApplicationContext applicationContext;

    public LikeService getLikeService() {
        String storageType = environment.getProperty("film.storage.type", "memory");
        log.info("Selecting like service for storage type: {}", storageType);

        try {
            return switch (storageType.toLowerCase()) {
                case "db" -> applicationContext.getBean("likeDBService", LikeService.class);
                default -> applicationContext.getBean("likeIMService", LikeService.class);
            };
        } catch (BeansException ex) {
            log.error("Failed to get like service implementation for type: {}", storageType, ex);
            throw new IllegalStateException("No available like service implementation for type: " + storageType, ex);
        }
    }

}
