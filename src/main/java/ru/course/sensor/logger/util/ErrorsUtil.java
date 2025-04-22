package ru.course.sensor.logger.util;

import org.springframework.validation.BindingResult;

public class ErrorsUtil {
    public static void recordErrors(StringBuilder errors, BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach(
                error -> errors
                        .append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; ")
        );
    }
}
