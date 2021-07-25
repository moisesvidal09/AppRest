package com.company.AppRest.entity.model;

import com.company.AppRest.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pessoa_id")
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Usuario usuario;

}
