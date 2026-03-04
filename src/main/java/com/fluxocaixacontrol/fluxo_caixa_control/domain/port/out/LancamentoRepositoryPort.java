package com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// IMPORTANT: sem método de UPDATE nem DELETE
// Lançamentos são append-only por design

public interface LancamentoRepositoryPort {

    Lancamento salvar(Lancamento lancamento);
    Optional<Lancamento> buscarPorId(UUID id);
    Optional<Lancamento> buscarPorChaveIdempotencia(String chave);
    List<Lancamento> listarPorData(LocalDate data);
    long contarPorData(LocalDate data);

}
