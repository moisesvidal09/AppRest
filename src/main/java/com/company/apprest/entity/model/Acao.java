package com.company.apprest.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acao_id")
    private Long id;

    @NotBlank
    private String codigoAcao;

}
