package me.braun.missionservice.data.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "misions", indexes = {
        @Index(name = "idx_mission_id", columnList = "id")
})

public final class Mission {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "name")
    private String name;
    private String description;

    @Setter
    @Getter

    @Column(name = "cust_id")
    private Long customerId;

    @Setter
    @Getter

    @Column(name = "craft_id")
    private Long spaceCraftId;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @Setter
    @Getter
    @Column(name = "curator_id")
    private Long curatorId;


    @Setter
    @Getter
    @Column(name = "payload_weight")
    private float payloadWeigh;
    private Date date;
    private int duration;

    @Setter
    @Getter
    @Column(name = "mision_price")
    private Long missionPrice;

    @Setter
    @Getter
    @Column(name = "service_price")
    private Long servicePrice;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Mission mission = (Mission) o;
        return id != null && Objects.equals(id, mission.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
