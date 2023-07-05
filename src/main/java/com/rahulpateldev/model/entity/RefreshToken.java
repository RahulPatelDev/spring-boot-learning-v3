package com.rahulpateldev.model.entity;

import com.rahulpateldev.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "refresh_token")
@EqualsAndHashCode(callSuper = true)
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}

