package com.company.AppRest.service;

import com.company.AppRest.entity.model.Pessoa;
import com.company.AppRest.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService implements IPessoaService{

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    @Cacheable("pessoas")
    public List<Pessoa> list(Pageable pageable) {

        Page<Pessoa> resultado = pessoaRepository.findAll(pageable);

        return resultado.hasContent() ? resultado.getContent() : new ArrayList<>();
    }

    @Override
    @Cacheable("pessoas")
    public Pessoa get(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id informado não encontrado no banco"));
    }

    @Override
    @Cacheable("pessoas")
    public List<Pessoa> get(List<Long> ids) {

        return pessoaRepository.findAllById(ids);

    }

    @Override
    @CacheEvict(value = "pessoas", allEntries = true)
    public void delete(Long id) {
        pessoaRepository.delete(this.get(id));
    }

    @Override
    @CacheEvict(value = "pessoas", allEntries = true)
    public Pessoa update(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    @CacheEvict(value = "pessoas", allEntries = true)
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    @CacheEvict(value = "pessoas", allEntries = true)
    public List<Pessoa> updateList(List<Pessoa> pessoas) {

        return pessoas.parallelStream()
                      .map(this::update)
                      .collect(Collectors.toList());
    }
}
