package ru.yandex.practicum.filmorate.controller.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.GenresService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenresServiceFactory {
    private final ApplicationContext applicationContext;

    public GenresService getGenresService() {
        try {
            return applicationContext.getBean("genresDBService", GenresService.class);
        } catch (BeansException ex) {
            throw new IllegalStateException("No available genresDBService service implementation for type: ", ex);
        }
    }
}
