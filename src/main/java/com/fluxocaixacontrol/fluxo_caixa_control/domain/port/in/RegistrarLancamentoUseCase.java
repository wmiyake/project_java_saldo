package com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RegistrarLancamentoUseCase {
    Lancamento registrar (TipoLancamento tipo, BigDecimal valor,
                          String descricao, LocalDateTime dataHora,
                          String chaveIdempotencia);

    // Exceção como classe interna — boa prática para erros de domínio
    // Tratamento de Exceção como classe interna para tratar erros de dominio
    class LancamentoDuplicadoException extends RuntimeException{
        private final Lancamento lancamentoExistente;

        public LancamentoDuplicadoException(Lancamento l){
            super("Lançamento já regisstrado: " + l.chaveIdempodencia());
            this.lancamentoExistente = l;
        }

        public Lancamento getLancamentoExistente() { return lancamentoExistente; }
    }
}