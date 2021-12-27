package me.braun.identityservice.data.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "accounts", indexes = {
        @Index(name = "phone", columnList = "phone", unique = true),
        @Index(name = "email", columnList = "email", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = {"id"})
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "phone", length = 12)
    private String phone;
    @Builder.Default
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role = new Role(1L, "customer");

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;


}