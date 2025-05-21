package ru.yandex.practicum.filmorate.controller.factory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.UserService;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceFactory {

    private final Environment environment;
    private final ApplicationContext applicationContext;

    public UserService getUserService() {
        String storageType = environment.getProperty("film.storage.type", "memory");
        try {
            return switch (storageType.toLowerCase()) {
                case "db" -> applicationContext.getBean("userDBService", UserService.class);
                default -> applicationContext.getBean("userIMService", UserService.class);
            };
        } catch (BeansException ex) {
            throw new IllegalStateException("No available user service implementation for type: " + storageType, ex);
        }
    }

}
