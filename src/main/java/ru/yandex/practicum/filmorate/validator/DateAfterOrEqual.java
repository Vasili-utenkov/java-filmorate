package ru.yandex.practicum.filmorate.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateAfterOrEqualValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAfterOrEqual {
    String minDate();
    String message() default "Дата релиза не может быть раньше {minDate}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}