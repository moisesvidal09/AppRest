package com.company.apprest.service;

import com.company.apprest.entity.model.Pessoa;

import java.util.List;

public interface IPessoaService extends CrudService<Pessoa>{

    //caso tenha metodo especifico de pessoa

    List<Pessoa> updateList(List<Pessoa> pessoas);

    Pessoa getPessoaByToken(String token);

}
