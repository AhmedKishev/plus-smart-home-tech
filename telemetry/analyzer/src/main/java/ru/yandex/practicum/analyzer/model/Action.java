package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

@Entity
@Getter
@Builder
@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SecondaryTable(name = "scenario_actions", pkJoinColumns = @PrimaryKeyJoinColumn(name = "action_id"))
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @Enumerated(EnumType.STRING)
     ActionTypeAvro type;

     Integer value;

    @ManyToOne
    @JoinColumn(name = "scenario_id", table = "scenario_actions")
     Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "sensor_id", table = "scenario_actions")
     Sensor sensor;
}
