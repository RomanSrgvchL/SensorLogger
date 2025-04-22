package ru.course.sensor.logger.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SensorDTO {
    @Size(min = 3, max = 30)
    @NotEmpty
    private String name;
}
