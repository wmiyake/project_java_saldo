package com.fluxocaixacontrol.fluxo_caixa_control.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record  SaldoConsolidado(
        Long id,
        LocalDate data,
        BigDecimal totalCreditos,
        BigDecimal totalDebitos,
        BigDecimal saldo,
        int quantidadeLancamentos,
        LocalDateTime consolidadoEm,
        StatusConsolidado status
        ) {

    public SaldoConsolidado {
        if (data == null)
            throw new IllegalArgumentException("Data é obrigatória");
        // Valida consistêbcia: Saldo deve ser créditos - débitos
        var saldoEsperado = totalCreditos.subtract(totalDebitos);
        if (saldo.compareTo(saldoEsperado) != 0)
            throw new IllegalArgumentException("Saldo inconsistente");
    }

    public static SaldoConsolidado calcular(LocalDate data,
                                             BigDecimal creditos,
                                             BigDecimal debitos,
                                             int quantidade) {
        return new SaldoConsolidado(
                null, data, creditos, debitos,
                creditos.subtract(debitos),
                quantidade, LocalDateTime.now(),
                StatusConsolidado.PROCESSADO
        );
    }

    public boolean isSaldoPositivo(){
        return saldo.compareTo(BigDecimal.ZERO) >= 0;
    }

    public enum StatusConsolidado { PROCESSANDO, PROCESSADO, ERRO }
}
