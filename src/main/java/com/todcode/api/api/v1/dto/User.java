package com.todcode.api.api.v1.dto;

import com.todcode.api.api.v1.dto.type.GanderType;
import com.todcode.api.api.v1.dto.type.RoleType;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 32)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotBlank
    @Size(max = 32)
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotBlank
    @Size(max = 32)
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "GANDER_TYPE", length = 8)
    private GanderType ganderType;

    @NotBlank
    @Size(max = 16)
    @Column(name = "USERNAME")
    private String username;

    @NotBlank
    @Size(max = 128)
    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @Column(name = "ROLES")
    private Set<Role> roles = new HashSet<Role>();
}
