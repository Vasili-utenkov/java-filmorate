package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.DateAfterOrEqual;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    //    целочисленный идентификатор —
    private Long id;
    //    название —
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    //    описание —
    @Size(min = 1, max = 200, message = "Максимальная длина описания фильма — 200 символов")
    @NotBlank(message = "Описание фильма не может быть пустым")
    private String description;
    //    дата релиза —
    @DateAfterOrEqual(
            minDate = "1895-12-28",
            message = "Дата релиза фильма не может быть раньше 28 декабря 1895 года"
    )
    private LocalDate releaseDate;
    //    продолжительность фильма —
    @NotNull(message = "Продолжительность фильма должна быть задана")
    @Min(value = 1,message = "Продолжительность фильма должна быть положительным числом")
    private Integer duration;

}
