package com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence;

import com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.entity.LancamentoEntity;
import com.fluxocaixacontrol.fluxo_caixa_control.adapter.out.persistence.jpa.LancamentoJpaRepository;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.TipoLancamento;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.out.LancamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LancamentoRepositoryAdapter implements LancamentoRepositoryPort {

    private final LancamentoJpaRepository jpaRepository;

    @Override
    public Lancamento salvar(Lancamento l) {
        return toDomain(jpaRepository.save(toEntity(l)));
    }

    @Override
    public Optional<Lancamento> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Lancamento> buscarPorChaveIdempotencia(String chave) {
        return jpaRepository.findByChaveIdempotencia(chave).map(this::toDomain);
    }

    @Override
    public List<Lancamento> listarPorData(LocalDate data) {
        return jpaRepository.findByData(data).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long contarPorData(LocalDate data) {
        return jpaRepository.countByData(data);
    }

    // Conversores explícitos — mais claros que MapStruct para aprendizado
    private LancamentoEntity toEntity(Lancamento l) {
        return LancamentoEntity.builder()
                .id(l.id()).tipo(l.tipo().name()).valor(l.valor())
                .descricao(l.descricao()).dataHora(l.dataHora())
                .chaveIdempotencia(l.chaveIdempotencia())
                .criadoEm(l.criadoEm()).build();
    }

    private Lancamento toDomain(LancamentoEntity e) {
        return new Lancamento(
                e.getId(), TipoLancamento.valueOf(e.getTipo()),
                e.getValor(), e.getDescricao(), e.getDataHora(),
                e.getChaveIdempotencia(), e.getCriadoEm()
        );
    }

}
