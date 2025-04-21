package ru.course.sensor.logger.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.course.sensor.logger.dto.MeasurementDTO;
import ru.course.sensor.logger.models.Measurement;
import ru.course.sensor.logger.models.Sensor;
import ru.course.sensor.logger.services.MeasurementsService;
import ru.course.sensor.logger.services.SensorsService;
import ru.course.sensor.logger.util.MeasurementErrorResponse;
import ru.course.sensor.logger.util.MeasurementsNotCreatedException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService,
                                  ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> getMeasurements() {
        List<MeasurementDTO> measurements = measurementsService.findAll()
                .stream().map(this::convertToMeasurementDTO).toList();
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(
                    error -> errors
                            .append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("; ")
            );
            throw new MeasurementsNotCreatedException(errors.toString());
        }
        Measurement measurement = convertToMeasurement(measurementDTO);
        Optional<Sensor> sensor = sensorsService.findByName(measurementDTO.getSensor().getName());
        if (sensor.isEmpty()) {
            throw new MeasurementsNotCreatedException("Sensor with the specified name not found");
        }
        measurement.setSensor(sensor.get());
        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getRainyDaysCount() {
        Integer rainyDaysCount = (int) measurementsService.findAll().stream().filter(Measurement::getRaining).count();
        return ResponseEntity.ok(rainyDaysCount);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(Exception e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
