package com.fluxocaixacontrol.fluxo_caixa_control.batch;


import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.LancamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@StepScope   // necessário para receber parâmetros do job
@RequiredArgsConstructor
public class ConsolidadoReader implements ItemReader<LocalDate> {
    private final LancamentoRepositoryPort lancamentoRepository;

    @Value("#{jobParameters['data']}")
    private String dataParam;

    private boolean processado = false;

    @Override
    public LocalDate read() {
        if (processado) return null; // null = fim da leitura

        var data = LocalDate.parse(dataParam);
        processado = true;

        if (lancamentoRepository.contarPorData(data) == 0) {
            return null; // sem dados, pula
        }
        return data;
    }
}
