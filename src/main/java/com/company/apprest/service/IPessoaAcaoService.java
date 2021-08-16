package com.company.apprest.service;

import com.company.apprest.entity.model.PessoaAcao;

import java.util.List;

public interface IPessoaAcaoService extends CrudService<PessoaAcao>{

    List<PessoaAcao> getAcoesByToken(String token);

}
