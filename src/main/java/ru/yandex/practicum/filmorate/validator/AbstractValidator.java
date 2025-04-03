package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractValidator<T> {

    List<String> errorValidationList = new ArrayList<>();

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public List<String> getErrorValidationList() {
        return errorValidationList;
    }

    public boolean validate(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(object);
        violations.stream().forEach(System.out::println);
        if (!violations.isEmpty()) {
            log.error("Ошибка валидации объекта:");

            errorValidationList = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .peek(message -> log.error(" - {}", message))
                    .collect(Collectors.toList());

            //2:violations.forEach(violation -> log.error(" - {}", violation.getMessage()));
            //1: throw new IllegalArgumentException("Ошибка валидации объекта");
            result = false;


        } else {
            log.info("Объект успешно валидирован");
            result = true;
        }

        return isResult();
    }

}
