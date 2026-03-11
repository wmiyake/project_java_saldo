package com.fluxocaixacontrol.fluxo_caixa_control.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Lancamento(
        UUID id,
        TipoLancamento tipo,
        BigDecimal valor,
        String descricao,
        LocalDateTime dataHora,
        String chaveIdempodencia,
        LocalDateTime criadoEm) {

    //Validar no momento da criação
    public Lancamento {
        if(tipo == null)
            throw new IllegalArgumentException("Tipo é obrigatório");
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("O valor deve ser positivo");
        if(descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição é obrigatória");
        if(dataHora == null)
            throw new IllegalArgumentException("Data/Hora é obrigatório");
        if(chaveIdempodencia == null)
            throw new IllegalArgumentException("Chave Idempotência é obrigastória");
    }

    //Factory method -  único ponto de criação
    public static Lancamento criar(TipoLancamento tipo, BigDecimal valor,
                                   String descricao, LocalDateTime dataHora,
                                   String chaveIdempodencia ){
        return new Lancamento(
                UUID.randomUUID(), tipo, valor, descricao,
                dataHora, chaveIdempodencia, LocalDateTime.now()
        );
    }

    public boolean isCredito() {
        return TipoLancamento.CREDITO.equals(tipo);
    }

    public boolean isDebito(){
        return TipoLancamento.DEBITO.equals(tipo);
    };
}