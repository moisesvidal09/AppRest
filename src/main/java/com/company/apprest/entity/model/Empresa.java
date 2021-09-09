package com.company.apprest.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String segmento;

    @NotBlank
    private String cnpj;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "empresa_id")
    private Set<Acao> acoes;

}
