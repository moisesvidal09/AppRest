package com.company.AppRest.task;

import com.company.AppRest.thread.EmpresaApiThread;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class EmpresaApiTask {


    public void executaThread(){

        new EmpresaApiThread().start();

    }

}
