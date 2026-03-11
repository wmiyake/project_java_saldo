package com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.messaging;


import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.EventPublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "feature.aws-sqs-enabled",
        havingValue = "false", matchIfMissing = true)
@Slf4j
public class LogEventPublisherAdapter implements EventPublisherPort {

    @Override
    public void publicarLancamentoRegistrado(Lancamento lancamento) {
        // FASE LOCAL: só loga
        // FASE AWS: substituir por SqsEventPublisherAdapter
        log.info("[EVENTO] LancamentoRegistrado: id={}, tipo={}, valor={}",
                lancamento.id(), lancamento.tipo(), lancamento.valor());
    }
}
