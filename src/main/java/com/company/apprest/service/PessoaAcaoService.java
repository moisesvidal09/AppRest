package com.company.apprest.service;

import com.company.apprest.entity.model.Pessoa;
import com.company.apprest.entity.model.PessoaAcao;
import com.company.apprest.entity.response.AcaoResponseDto;
import com.company.apprest.entity.response.PessoaAcaoPrecoMedioResponseDto;
import com.company.apprest.repository.PessoaAcaoRepository;
import com.company.apprest.repository.PessoaRepository;
import com.company.apprest.repository.UsuarioRepository;
import com.company.apprest.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PessoaService pessoaService;

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

        Pessoa pessoa = pessoaService.getPessoaByToken(token);

        return pessoaAcaoRepository.findByPessoa(pessoa);
    }

    public PessoaAcaoPrecoMedioResponseDto getPrecoMedioAcao(String token, Long acao_id) throws Exception {

        Pessoa pessoa = pessoaService.getPessoaByToken(token);

        List<PessoaAcao> acoes = pessoaAcaoRepository.findWhereValorVendaIsNullByPessoaIdAndAcaoId(pessoa.getId(), acao_id);

        if(acoes == null || acoes.isEmpty())
            throw new Exception("Não foi possível encontrar ações com id: " + acao_id + " ou com pessoa id " + pessoa.getId());

        DecimalFormat df = new DecimalFormat("R$ #,##0.00");

       return PessoaAcaoPrecoMedioResponseDto.builder()
                                            .acao(mapper.map(acoes.get(0), AcaoResponseDto.class))
                                            .precoMedio(df.format(this.calculaPrecoMedioAcao(acoes)))
                                            .build();
    }

    private BigDecimal calculaPrecoMedioAcao(List<PessoaAcao> acoes){

        List<BigDecimal> totalPagoPorAcao = acoes.stream()
                .map(a ->
                        new BigDecimal(a.getValorCompra().toString()).multiply(new BigDecimal(a.getQuantidade().toString()),new MathContext(4, RoundingMode.HALF_UP))
                )
                .collect(Collectors.toList());

        Double totalPago = totalPagoPorAcao.stream()
                .mapToDouble(BigDecimal::doubleValue).sum();

        Integer totalQuantidade = acoes.stream()
                .map(PessoaAcao::getQuantidade)
                .mapToInt(Integer::intValue).sum();

        return BigDecimal.valueOf(totalPago / totalQuantidade);
    }
}
