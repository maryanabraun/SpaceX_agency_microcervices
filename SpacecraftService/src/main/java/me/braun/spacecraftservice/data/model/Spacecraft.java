package me.braun.spacecraftservice.data.model;

import lombok.*;
import me.braun.spacecraftservice.data.model.CraftName;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "spacecrafts")
public class Spacecraft {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "name_id")
    private CraftName craftName;

    @Getter
    @Setter
    @Column(name = "capacity", nullable = false)
    private Double capacity;

    @Getter
    @Setter
    @Column(name = "max_weight", nullable = false)
    private Integer maxWeight;

    @Getter
    @Setter
    @Column(name = "launch_price", nullable = false)
    private Integer launchPrice;


}