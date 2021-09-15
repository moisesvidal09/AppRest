package com.company.apprest.service;

import com.company.apprest.entity.model.Pessoa;
import com.company.apprest.entity.model.Role;
import com.company.apprest.entity.model.Usuario;
import com.company.apprest.enums.EstadoUsuario;
import com.company.apprest.exception.UsuarioException;
import com.company.apprest.repository.PessoaRepository;
import com.company.apprest.repository.RoleRepository;
import com.company.apprest.repository.UsuarioRepository;
import com.company.apprest.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PessoaService implements IPessoaService{

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private static Logger logger = LoggerFactory.getLogger(PessoaService.class);

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
    public Pessoa save(Pessoa pessoa) throws UsuarioException {

        if(usuarioRepository.findByUsername(pessoa.getUsuario().getUsername()) != null) {
            logger.error("E-mail: "+pessoa.getUsuario().getUsername()+" já possui cadastro !!!");
            throw new UsuarioException("E-mail informado já cadastrado !!!");
        }

        userDetailsService.validateSenha(pessoa.getUsuario().getPassword());

        pessoa.getUsuario().setPassword(passwordEncoder.encode(pessoa.getUsuario().getPassword()));
        pessoa.getUsuario().setEstadoUsuario(EstadoUsuario.DESATIVADO);

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findByNome("USER"));

        pessoa.getUsuario().setRoles(roles);

        return pessoaRepository.save(pessoa);
    }

    @Override
    @CacheEvict(value = "pessoas", allEntries = true)
    public List<Pessoa> updateList(List<Pessoa> pessoas) {

        return pessoas.parallelStream()
                      .map(this::update)
                      .collect(Collectors.toList());
    }

    @Override
    public Pessoa getPessoaByToken(String token) {

        token = token.replace("Bearer", "").trim();

        UserDetails user = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));

        Usuario usuario = usuarioRepository.findByUsername(user.getUsername());

        return  pessoaRepository.findByUsuario(usuario);
    }
}
