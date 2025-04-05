package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DescriptionIsNullOrLessThenValidator implements ConstraintValidator<DescriptionIsNullOrLessThen, String> {
    private long maxLength;

    @Override
    public void initialize(DescriptionIsNullOrLessThen constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        return description == null || description.length() <= maxLength;
    }
}
