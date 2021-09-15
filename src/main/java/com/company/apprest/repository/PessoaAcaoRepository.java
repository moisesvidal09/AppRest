package com.company.apprest.repository;

import com.company.apprest.entity.model.Pessoa;
import com.company.apprest.entity.model.PessoaAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaAcaoRepository extends JpaRepository<PessoaAcao, Long> {

    List<PessoaAcao> findByPessoa(Pessoa pessoa);

    @Query(value = "SELECT * FROM pessoa_acao pa WHERE pa.valor_venda IS NULL AND pa.pessoa_id = :pessoa_id AND pa.acao_id = :acao_id",
            nativeQuery = true)
    List<PessoaAcao> findWhereValorVendaIsNullByPessoaIdAndAcaoId(@Param("pessoa_id") Long pessoa_id, @Param("acao_id") Long acao_id);

}
