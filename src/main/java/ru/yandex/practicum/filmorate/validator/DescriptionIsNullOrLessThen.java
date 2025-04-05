package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DescriptionIsNullOrLessThenValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionIsNullOrLessThen {
    long maxLength();
    String message() default "максимальная длина описания — {maxLength} символов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
