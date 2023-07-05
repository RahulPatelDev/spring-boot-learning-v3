package com.rahulpateldev.model.entity;

import com.rahulpateldev.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User extends BaseEntity implements UserDetails {

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    @NotNull
    @Size(min = 5, max = 25)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Digits(integer = 3, fraction = 0)
    private Integer age;

    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired = true;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked = true;

    @Column(name = "is_credential_non_expired")
    private boolean isCredentialsNonExpired = true;

    @Column(name = "is_account_enabled")
    private boolean isEnabled = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    })
    private List<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            role.getPermissions().forEach(
                    permission -> authorities.add(
                            new SimpleGrantedAuthority(
                                    permission.getName().name())));
        });
        return authorities;
    }
}
