package com.saurabhorg.uber.uberApllication.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "driver", indexes = {
        @Index(name = "idx_driver_vehicle_id", columnList = "vehicleId")
})
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long id;

    private Double rating = 0.0;
    private Integer ratingCount = 0;

    private Boolean available;
    @JsonProperty("vehicle_id")
    private String vehicleId;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    Point currentLocation;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
