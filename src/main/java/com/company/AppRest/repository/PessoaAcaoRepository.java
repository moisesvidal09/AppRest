package com.company.AppRest.repository;

import com.company.AppRest.entity.model.Pessoa;
import com.company.AppRest.entity.model.PessoaAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaAcaoRepository extends JpaRepository<PessoaAcao, Long> {

    List<PessoaAcao> findByPessoa(Pessoa pessoa);

}
