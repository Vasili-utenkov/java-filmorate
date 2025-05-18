package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {
    private Long id;
    //    электронная почта —
    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Не верный формат для электронной почты")
    private String email;
    //    логин пользователя —
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^[^\\s]+$", message = "Пароль не должен содержать пробелы")
    private String login;

    //    имя для отображения —
    private String name = "";
    //    дата рождения —
    @NotNull(message = "Дата рождения обязательна к заполнению")
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    public void setNameThenNull() {
        if (this.getName() == null || this.getName().isBlank()) {
            this.setName(getLogin());
        }
    }

}
