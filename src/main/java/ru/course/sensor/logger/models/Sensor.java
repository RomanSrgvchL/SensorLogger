package ru.course.sensor.logger.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 3, max = 30)
    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;
}
