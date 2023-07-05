package com.rahulpateldev.model.entity;

import com.rahulpateldev.enums.Permission;
import com.rahulpateldev.model.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Permissions extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Permission name;

}
