package ru.course.sensor.logger.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.course.sensor.logger.models.Measurement;
import ru.course.sensor.logger.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurements(measurement);
        measurementsRepository.save(measurement);
    }

    private void enrichMeasurements(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }
}
