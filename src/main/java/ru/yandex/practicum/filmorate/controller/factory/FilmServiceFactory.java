package ru.yandex.practicum.filmorate.controller.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.FilmService;


@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceFactory {
    private final Environment environment;
    private final ApplicationContext applicationContext;

    public FilmService getFilmService() {
        String storageType = environment.getProperty("film.storage.type", "memory");

        try {
            return switch (storageType.toLowerCase()) {
                case "db" -> applicationContext.getBean("filmDBService", FilmService.class);
                default -> applicationContext.getBean("filmIMService", FilmService.class);
            };
        } catch (BeansException ex) {
            throw new IllegalStateException("No available film service implementation for type: " + storageType, ex);
        }
    }
}