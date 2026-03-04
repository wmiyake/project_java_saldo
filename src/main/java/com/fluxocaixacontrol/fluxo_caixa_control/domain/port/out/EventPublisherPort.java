package com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;

// Hoje: loga o evento. Amanhã (AWS): publica no SQS.
// O domínio não sabe qual das duas — depende do adaptador ativo.
public interface EventPublisherPort {
    void publicarLancamentoRegistrado(Lancamento lancamento);
}
