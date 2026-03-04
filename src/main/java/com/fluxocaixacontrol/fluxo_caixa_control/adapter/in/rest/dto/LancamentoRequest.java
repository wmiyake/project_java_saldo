package com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.TipoLancamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LancamentoRequest(
@NotNull TipoLancamento tipo,

@NotNull @DecimalMin("0.01")
BigDecimal valor,

@NotBlank @Size(max = 255)
String descricao,

@NotNull
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
LocalDateTime dataHora
)  {}
