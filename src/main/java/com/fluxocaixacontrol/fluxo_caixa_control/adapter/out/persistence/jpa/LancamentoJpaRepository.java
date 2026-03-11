package com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.jpa;

import com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.entity.LancamentoEntity;
import com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.entity.SaldoConsolidadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LancamentoJpaRepository extends JpaRepository<LancamentoEntity, UUID> {

    Optional<LancamentoEntity> findByChaveIdempotencia(String chave);

    @Query("SELECT l FROM LancamentoEntity l " +
            "WHERE CAST(l.dataHora AS LocalDate) = :data " +
            "ORDER BY l.dataHora ASC")
    List<LancamentoEntity> findByData(@Param("data") LocalDate data);

    @Query("SELECT COUNT(l) FROM LancamentoEntity l " +
            "WHERE CAST(l.dataHora AS LocalDate) = :data")
    long countByData(@Param("data") LocalDate data);
}

