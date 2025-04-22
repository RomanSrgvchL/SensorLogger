package ru.course.sensor.logger;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.course.sensor.logger.dto.MeasurementDTO;
import ru.course.sensor.logger.dto.SensorDTO;

import java.util.Random;

@SpringBootApplication
public class SensorLoggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SensorLoggerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner clientImitationRunner() {
        return _ -> clientImitation();
    }

    public static void clientImitation() {
        RestTemplate restTemplate = new RestTemplate();

        String registerSensorUrl = "http://localhost:8080/sensors/registration";
        String addMeasurementsUrl = "http://localhost:8080/measurements/add";
        String getMeasurementsUrl = "http://localhost:8080/measurements";

        SensorDTO sensorDTO = new SensorDTO("sensor_1");

        registerSensor(restTemplate, registerSensorUrl, sensorDTO);

        addMeasurements(restTemplate, addMeasurementsUrl, sensorDTO);

        getMeasurements(restTemplate, getMeasurementsUrl);
    }

    public static void registerSensor(RestTemplate restTemplate, String url, SensorDTO sensorDTO) {
        HttpEntity<SensorDTO> request = new HttpEntity<>(sensorDTO);
        restTemplate.postForObject(url, request, Void.class);
    }

    public static void addMeasurements(RestTemplate restTemplate, String url, SensorDTO sensorDTO) {
        Random random = new Random();

        MeasurementDTO measurementDTO;
        HttpEntity<MeasurementDTO> request;

        for (int i = 0; i < 1000; i++) {
            double value = random.nextDouble(-30, 45);
            measurementDTO = new MeasurementDTO(value, sensorDTO, System.currentTimeMillis() % 2 == 0);
            request = new HttpEntity<>(measurementDTO);
            restTemplate.postForObject(url, request, Void.class);
        }
    }

    public static void getMeasurements(RestTemplate restTemplate, String url) {
        MeasurementDTO[] measurements = restTemplate.getForObject(url, MeasurementDTO[].class);
        if (measurements != null) {
            for (var measurement : measurements) {
                System.out.println(measurement);
            }
        }
    }
}
