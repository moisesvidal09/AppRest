package com.company.apprest.entity.model;

import com.company.apprest.enums.EstadoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private EstadoUsuario estadoUsuario;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles;

}
