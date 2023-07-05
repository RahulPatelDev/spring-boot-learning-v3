package com.rahulpateldev.model.common;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //    @SequenceGenerator(
    //            name = "user_seq_generator",
    //            allocationSize = 1
    //    )
    //    @GeneratedValue(
    //            strategy = GenerationType.SEQUENCE,
    //            generator = "user_seq_generator"
    //    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "last_modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt = new Date();

    @Column(updatable = false, unique = true, nullable = false)
    private String uuid = UUID.randomUUID().toString();

    @PrePersist
    public void autoSetUUID() {
        this.setUuid(UUID.randomUUID().toString());
    }

}
