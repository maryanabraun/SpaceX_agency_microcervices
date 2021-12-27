package me.braun.missionservice.data.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statuses")
public class Status {
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Status status = (Status) o;
        return id != null && Objects.equals(id, status.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
