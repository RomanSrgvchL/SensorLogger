package ru.course.sensor.logger.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MeasurementDTO {
    @Range(min = -100, max = 100)
    private int value;

    private boolean raining;

    @NotEmpty
    private LocalDateTime createdAt;
}
