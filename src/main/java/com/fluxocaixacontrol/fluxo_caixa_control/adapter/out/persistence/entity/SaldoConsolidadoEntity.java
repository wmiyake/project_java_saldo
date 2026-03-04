package com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.entity;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "saldo_consolidado")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaldoConsolidadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate data;

    @Column(name = "total_creditos", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCreditos;

    @Column(name = "total_debitos", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalDebitos;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @Column(name = "quantidade_lancamentos", nullable = false)
    private int quantidadeLancamentos;

    @Column(name = "consolidado_em", nullable = false)
    private LocalDateTime consolidadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SaldoConsolidado.StatusConsolidado status;
}
