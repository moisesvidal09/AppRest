package com.company.apprest.service;

import com.company.apprest.entity.model.Empresa;
import com.company.apprest.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService implements IEmpresaService{

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    @Cacheable("empresas")
    public List<Empresa> list(Pageable pageable) {

        Page<Empresa> pagina = empresaRepository.findAll(pageable);

        return pagina.hasContent() ? pagina.getContent() : new ArrayList<>();
    }

    @Override
    @Cacheable("empresas")
    public Empresa get(Long id) {
        return empresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada!!!"));
    }

    @Override
    @Cacheable("empresas")
    public List<Empresa> get(List<Long> ids) {
        return empresaRepository.findAllById(ids);
    }

    @Override
    @CacheEvict(value = "empresas", allEntries = true)
    public void delete(Long id) {
        empresaRepository.delete(get(id));
    }

    @Override
    @CacheEvict(value = "empresas", allEntries = true)
    public Empresa update(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    @CacheEvict(value = "empresas", allEntries = true)
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

}
