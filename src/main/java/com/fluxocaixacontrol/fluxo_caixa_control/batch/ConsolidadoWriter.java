package com.fluxocaixacontrol.fluxo_caixa_control.batch;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.SaldoConsolidadoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsolidadoWriter implements ItemWriter<SaldoConsolidado> {

    private final SaldoConsolidadoRepositoryPort saldoRepository;

    @Override
    public void write(Chunk<? extends SaldoConsolidado> chunk) {
        chunk.forEach(saldoRepository::salvarOuAtualizar);
    }
}
