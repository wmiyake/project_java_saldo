package com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest.dto;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

// LancamentoResponse.java (dentro de ApiResponse.java)
public record LancamentoResponse(UUID id, TipoLancamento tipo, BigDecimal valor,
                                 String descricao, LocalDateTime dataHora,
                                 LocalDateTime criadoEm, String status
) {
    public static LancamentoResponse from(Lancamento l) {
        return new LancamentoResponse(
                l.id(), l.tipo(), l.valor(), l.descricao(),
                l.dataHora(), l.criadoEm(), "REGISTRADO"
        );
    }
}