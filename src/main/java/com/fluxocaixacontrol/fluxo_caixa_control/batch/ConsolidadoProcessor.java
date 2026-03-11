package com.fluxocaixacontrol.fluxo_caixa_control.batch;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.LancamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ConsolidadoProcessor implements ItemProcessor<LocalDate, SaldoConsolidado> {

    private final LancamentoRepositoryPort lancamentoRepository;

    @Override
    public SaldoConsolidado process(LocalDate data) {
        var lancamentos = lancamentoRepository.listarPorData(data);
        if (lancamentos.isEmpty()) return null; // null = Spring Batch pula

        var creditos = lancamentos.stream()
                .filter(Lancamento::isCredito)
                .map(Lancamento::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var debitos = lancamentos.stream()
                .filter(Lancamento::isDebito)
                .map(Lancamento::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return SaldoConsolidado.calcular(data, creditos, debitos, lancamentos.size());
    }
}
