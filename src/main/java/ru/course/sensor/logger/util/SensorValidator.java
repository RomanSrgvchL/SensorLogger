package ru.course.sensor.logger.util;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.course.sensor.logger.dto.SensorDTO;
import ru.course.sensor.logger.models.Sensor;
import ru.course.sensor.logger.services.SensorsService;

import java.util.Optional;

@Controller
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        Optional<Sensor> sameNameSensor = sensorsService.findByName(sensorDTO.getName());

        if (sameNameSensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name already exists");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }
}
