package ru.course.sensor.logger.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeasurementDTO {
    @NotNull
    @Range(min = -100, max = 100)
    private Double value;

    @NotNull
    private SensorDTO sensor;

    @NotNull
    private Boolean raining;
}
