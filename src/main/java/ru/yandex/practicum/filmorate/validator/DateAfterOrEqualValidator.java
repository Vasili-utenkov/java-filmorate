package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateAfterOrEqualValidator implements ConstraintValidator<DateAfterOrEqual, LocalDate> {
    private String minDate;
    private LocalDate parsedMinDate;


    @Override
    public void initialize(DateAfterOrEqual constraintAnnotation) {
        minDate = constraintAnnotation.minDate();
        parsedMinDate = LocalDate.parse(minDate);
    }

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        return releaseDate != null && releaseDate.isAfter(parsedMinDate);
    }
}