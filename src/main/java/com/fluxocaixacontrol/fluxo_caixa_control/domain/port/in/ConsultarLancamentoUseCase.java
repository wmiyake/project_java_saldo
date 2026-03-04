package com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultarLancamentoUseCase {
    Lancamento buscarPorId(UUID id);
    List<Lancamento> listarPorData(LocalDate data);

    class LancamentoNaoEncontradoException extends RuntimeException{
        public LancamentoNaoEncontradoException(UUID id) {

            super("Lacamento não encontrado: " + id);
        }
    }
}
