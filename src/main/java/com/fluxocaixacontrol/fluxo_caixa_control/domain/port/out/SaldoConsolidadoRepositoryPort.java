package com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaldoConsolidadoRepositoryPort {

    SaldoConsolidado salvarOuAtualizar(SaldoConsolidado saldo); // permite upsert
    Optional<SaldoConsolidado> buscarPorData(LocalDate data);
    List<SaldoConsolidado> listarPorPeriodo(LocalDate inicio, LocalDate fim);
}
