package ru.yandex.practicum.filmorate.controller.factory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.MpaService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaServiceFactory {

    private final ApplicationContext applicationContext;

    public MpaService getMPAService() {
        try {
            return applicationContext.getBean("mpaDBService", MpaService.class);
        } catch (BeansException ex) {
            throw new IllegalStateException("No available mpaDBService service implementation for type: ", ex);
        }
    }

}
