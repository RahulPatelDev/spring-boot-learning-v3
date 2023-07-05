package com.rahulpateldev.model.entity;

import com.rahulpateldev.enums.Role;
import com.rahulpateldev.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Roles extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Role name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permissions", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "permission_id", referencedColumnName = "id")
    })
    private List<Permissions> permissions;
}
