package ru.course.sensor.logger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.course.sensor.logger.models.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
