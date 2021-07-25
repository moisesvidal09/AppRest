package com.company.AppRest.service;

import com.company.AppRest.entity.model.PessoaAcao;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPessoaAcaoService extends CrudService<PessoaAcao>{

    List<PessoaAcao> getAcoesByToken(String token) throws Exception;

}
