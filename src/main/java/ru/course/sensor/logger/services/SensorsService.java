package ru.course.sensor.logger.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.course.sensor.logger.models.Sensor;
import ru.course.sensor.logger.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
