package ru.yandex.practicum.filmorate.service.db;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.Collection;

@Service("filmDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class FilmDBService implements FilmService {

    @Override
    public Collection<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        return null;
    }
}
