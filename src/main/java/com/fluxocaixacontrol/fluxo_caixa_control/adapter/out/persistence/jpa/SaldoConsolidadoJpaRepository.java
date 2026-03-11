package com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.jpa;

import com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.entity.SaldoConsolidadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// SaldoConsolidadoJpaRepository.java
@Repository
public interface SaldoConsolidadoJpaRepository extends JpaRepository<SaldoConsolidadoEntity, Long>
{
    Optional<SaldoConsolidadoEntity> findByData(LocalDate data);
    List<SaldoConsolidadoEntity> findByDataBetweenOrderByDataAsc(
            LocalDate inicio, LocalDate fim);
}
