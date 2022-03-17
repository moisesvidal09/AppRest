package com.company.apprest.service;

import com.company.apprest.exception.UsuarioException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService <T> {

    List<T> list(Pageable pageable);

    T get(Long id);

    List<T> get(List<Long> ids);

    void delete(Long id);

    T update(T entity);

    T save(T entity) throws UsuarioException;

}
