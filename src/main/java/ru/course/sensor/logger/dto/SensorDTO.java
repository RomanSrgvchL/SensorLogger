package ru.course.sensor.logger.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorDTO {
    @Size(min = 3, max = 30)
    private String name;
}
