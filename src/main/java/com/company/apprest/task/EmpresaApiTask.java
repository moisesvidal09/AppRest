package com.company.apprest.task;

import com.company.apprest.thread.EmpresaApiThread;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class EmpresaApiTask {


    public void executaThread(){

        new EmpresaApiThread().start();

    }

}
