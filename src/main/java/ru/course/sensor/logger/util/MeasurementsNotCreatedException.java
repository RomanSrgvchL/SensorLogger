package ru.course.sensor.logger.util;

public class MeasurementsNotCreatedException extends RuntimeException {
    public MeasurementsNotCreatedException(String message) {
        super(message);
    }
}
