package com.company.AppRest.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService <T extends Object> {

    List<T> list(Pageable pageable);

    T get(Long id);

    List<T> get(List<Long> ids);

    void delete(Long id);

    T update(T entity);

    T save(T entity);

}
