
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Создайте экземпляр фильма с некорректными данными
        Film film = new Film();
        film.setName("Updated Film"); // Пустое название
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2024, 01, 01)); // Дата раньше 28 декабря 1895
        film.setDuration(-120); // Отрицательная продолжительность

        // Создайте валидатор


        User user = new User();
        user.setEmail("email@mail.ru"); // Некорректный email
        user.setLogin("login"); // Логин с пробелами
        user.setName(""); // Пустое имя
        user.setBirthday(LocalDate.now().minusDays(1)); // Дата рождения в будущем

        // Создайте валидатор


    }
}