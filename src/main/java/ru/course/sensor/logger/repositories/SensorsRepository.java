package ru.course.sensor.logger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.course.sensor.logger.models.Sensor;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
