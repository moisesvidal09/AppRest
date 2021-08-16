package com.company.apprest.service;

import com.company.apprest.entity.model.Pessoa;
import com.company.apprest.entity.model.PessoaAcao;
import com.company.apprest.entity.model.Usuario;
import com.company.apprest.repository.PessoaAcaoRepository;
import com.company.apprest.repository.PessoaRepository;
import com.company.apprest.repository.UsuarioRepository;
import com.company.apprest.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaAcaoService implements IPessoaAcaoService{

    @Autowired
    private PessoaAcaoRepository pessoaAcaoRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Cacheable("pessoa_acao")
    public List<PessoaAcao> list(Pageable pageable) {

        Page<PessoaAcao> pagina = pessoaAcaoRepository.findAll(pageable);

        return pagina.hasContent() ? pagina.getContent() : new ArrayList<>();
    }

    @Override
    @Cacheable("pessoa_acao")
    public PessoaAcao get(Long id) {
        return pessoaAcaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa - acao nao encontrada!!!"));
    }

    @Override
    @Cacheable("pessoa_acao")
    public List<PessoaAcao> get(List<Long> ids) {
        return pessoaAcaoRepository.findAllById(ids);
    }

    @Override
    @CacheEvict(value = "pessoa_acao", allEntries = true)
    public void delete(Long id) {
        pessoaAcaoRepository.delete(this.get(id));
    }

    @Override
    @CacheEvict(value = "pessoa_acao", allEntries = true)
    public PessoaAcao update(PessoaAcao pessoaAcao) {
        return this.save(pessoaAcao);
    }

    @Override
    @CacheEvict(value = "pessoa_acao", allEntries = true)
    public PessoaAcao save(PessoaAcao pessoaAcao) {
        return pessoaAcaoRepository.save(pessoaAcao);
    }

    @Override
    public List<PessoaAcao> getAcoesByToken(String token){

        UserDetails user = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));

        Usuario usuario = usuarioRepository.findByUsername(user.getUsername());

        Pessoa pessoa = pessoaRepository.findByUsuario(usuario);

        return pessoaAcaoRepository.findByPessoa(pessoa);
    }


}
