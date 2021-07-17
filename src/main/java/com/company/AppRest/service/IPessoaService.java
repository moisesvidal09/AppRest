package com.company.AppRest.service;

import com.company.AppRest.entity.model.Pessoa;

import java.util.List;

public interface IPessoaService extends CrudService<Pessoa>{

    //caso tenha metodo especifico de pessoa

    List<Pessoa> updateList(List<Pessoa> pessoas);

}
