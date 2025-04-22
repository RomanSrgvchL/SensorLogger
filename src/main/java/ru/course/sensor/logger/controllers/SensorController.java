package ru.course.sensor.logger.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.course.sensor.logger.dto.SensorDTO;
import ru.course.sensor.logger.models.Sensor;
import ru.course.sensor.logger.services.SensorsService;
import ru.course.sensor.logger.util.ErrorsUtil;
import ru.course.sensor.logger.util.SensorErrorResponse;
import ru.course.sensor.logger.util.SensorNotCreatedException;
import ru.course.sensor.logger.util.SensorValidator;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    public SensorController(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensor, BindingResult bindingResult) {
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            ErrorsUtil.recordErrors(errors, bindingResult);
            throw new SensorNotCreatedException(errors.toString());
        }
        sensorsService.save(convertToSensor(sensor));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(Exception e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
