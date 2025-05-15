package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.DateAfterOrEqual;
import ru.yandex.practicum.filmorate.validator.DescriptionIsNullOrLessThen;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Film extends AbstaractType {
    private Long id;
    //    название —
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    //    описание —
    @DescriptionIsNullOrLessThen(
            maxLength = 200,
            message = "максимальная длина описания — 200 символов"
    )
    private String description;
    //    дата релиза —
    @DateAfterOrEqual(
            minDate = "1895-12-28",
            message = "Дата релиза фильма не может быть раньше 28 декабря 1895 года"
    )
    private LocalDate releaseDate;
    //    продолжительность фильма —
    @NotNull(message = "Продолжительность фильма должна быть задана")
    @Min(value = 1, message = "Продолжительность фильма должна быть положительным числом")
    private Integer duration;




    // категория фильма
    private int mpaID;

}
