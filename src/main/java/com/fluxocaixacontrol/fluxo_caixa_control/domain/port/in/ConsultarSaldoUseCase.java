package com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ConsultarSaldoUseCase {

    SaldoConsolidado consultarPorData(LocalDate data);

    class SaldoNaoDisponivelException extends RuntimeException{
        public SaldoNaoDisponivelException(LocalDate data){

            super("Saldo não disponivel para: " + data);
        }
    }
}
