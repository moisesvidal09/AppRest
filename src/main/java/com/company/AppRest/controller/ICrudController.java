package com.company.AppRest.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ICrudController <T extends Object>{

        ResponseEntity create(T entity);

        ResponseEntity get(Long id);

        ResponseEntity update(T entity);

        ResponseEntity<HttpStatus> delete(Long id);

        ResponseEntity getList(Pageable pageable);

}
