package com.fluxocaixacontrol.fluxo_caixa_control.domain.service;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.TipoLancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in.ConsultarSaldoUseCase;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.LancamentoRepositoryPort;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.SaldoConsolidadoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaldoConsolidadoService implements ConsultarSaldoUseCase {

    private final SaldoConsolidadoRepositoryPort saldoRepository;
    private final LancamentoRepositoryPort lancamentoRepository;

    @Override
    public SaldoConsolidado consultarPorData(LocalDate data) {
        // Estratégia 1: busca pré-calculado pelo Batch (rápido)
        return saldoRepository.buscarPorData(data)
                // Estratégia 2: calcula em tempo real como fallback
                .orElseGet(() -> calcularTempoReal(data));
    }

    @Transactional
    public SaldoConsolidado calcularEPersistir(LocalDate data) {
        var consolidado = calcularTempoReal(data);
        return saldoRepository.salvarOuAtualizar(consolidado);
    }

    private SaldoConsolidado calcularTempoReal(LocalDate data) {
        var lancamentos = lancamentoRepository.listarPorData(data);

        if (lancamentos.isEmpty()) {
            throw new SaldoNaoDisponivelException(data);
        }

        var creditos = lancamentos.stream()
                .filter(l -> TipoLancamento.CREDITO.equals(l.tipo()))
                .map(Lancamento::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var debitos = lancamentos.stream()
                .filter(l -> TipoLancamento.DEBITO.equals(l.tipo()))
                .map(Lancamento::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return SaldoConsolidado.calcular(data, creditos, debitos, lancamentos.size());
    }
}