package com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lancamentos")
@Immutable  // Hibernate nunca faz UPDATE nessa tabela
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoEntity {
    @Id
    @Column(updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false, length = 10)
    private String tipo;

    @Column(nullable = false, updatable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, updatable = false, length = 255)
    private String descricao;

    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @Column(name = "chave_idempotencia", nullable = false,
            updatable = false, unique = true, length = 100)
    private String chaveIdempotencia;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

}
