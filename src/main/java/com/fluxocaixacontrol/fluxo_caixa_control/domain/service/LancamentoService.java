package com.fluxocaixacontrol.fluxo_caixa_control.domain.service;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.TipoLancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in.ConsultarLancamentoUseCase;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in.RegistrarLancamentoUseCase;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.EventPublisherPort;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.LancamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LancamentoService implements RegistrarLancamentoUseCase, ConsultarLancamentoUseCase {

    private final LancamentoRepositoryPort lancamentoRepository;
    private final EventPublisherPort eventPublisher;

    @Override
    public Lancamento registrar (TipoLancamento tipo, BigDecimal valor,
                                 String descricao, LocalDateTime dataHora,
                                 String chaveIdempotencia){
        // 1. Idempotência: já existe? Retorna o existente
        var existente = lancamentoRepository.buscarPorChaveIdempotencia(chaveIdempotencia);
        if(existente.isPresent()){
            throw new LancamentoDuplicadoException(existente.get());
        }

        // 2. Cria (Record valida as regras de negócio no construtor)
        var lancamento = Lancamento.criar(tipo, valor, descricao, dataHora, chaveIdempotencia);

        // 3. Persiste (append-only)
        var salvo = lancamentoRepository.salvar(lancamento);

        // 4. Publica evento (hoje: log; AWS: SQS)
        eventPublisher.publicarLancamentoRegistrado(salvo);

        return salvo;
    }

    @Override
    @Transactional(readOnly = true)
    public Lancamento buscarPorId(UUID id) {
        return lancamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new LancamentoNaoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> listarPorData(LocalDate data) {
        return lancamentoRepository.listarPorData(data);
    }
}
