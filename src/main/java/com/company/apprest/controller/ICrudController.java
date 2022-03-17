package com.company.apprest.controller;

import com.company.apprest.exception.UsuarioException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ICrudController <T>{

        ResponseEntity create(T entity);

        ResponseEntity get(Long id);

        ResponseEntity update(T entity);

        ResponseEntity<HttpStatus> delete(Long id);

        ResponseEntity getList(Pageable pageable);

}
