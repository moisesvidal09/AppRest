package com.company.AppRest;

import com.company.AppRest.entity.model.PessoaAcao;
import com.company.AppRest.service.PessoaAcaoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PessoAcaoTest {

    @Autowired
    private PessoaAcaoService pessoaAcaoService;


    @Test
    public void list() throws Exception {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2lzZXN2aWRhbDA5IiwiZXhwIjoxNjI3MTUzNzIyLCJpYXQiOjE2MjcxMzU3MjJ9.xDlKNlIAoHzbH0-k1w8RiQYInlfJHfhZyZ6n6XMwspuWCDgVCMFMkyZ1nSQ5VMK3pYpuCa4tIMunZmH8N6TQRw";

        List<PessoaAcao> acoes = pessoaAcaoService.getAcoesByToken(token);

        Assert.assertFalse(acoes.isEmpty());
    }

}
