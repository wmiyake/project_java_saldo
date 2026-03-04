//package com.fluxocaixacontrol.fluxo_caixa_control.infra.legacy;
//
//import com.fluxocaixacontrol.fluxo_caixa_control.config.FeatureFlags;
//import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.Lancamento;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class LegacyAdapter {
//
//    private final FeatureFlags featureFlags;
//
//    public void replicarNoLegado(Lancamento lancamento) {
//        if (!featureFlags.isLegacyParallelEnabled()) return;
//
//        try {
//            // TODO: integrar com o mainframe aqui
//            // Opção 1: chamada REST para endpoint do legado
//            // Opção 2: insert direto no banco do legado
//            log.info("[LEGADO] Replicando lançamento: id={}", lancamento.id());
//        } catch (Exception e) {
//            // NUNCA falhar o novo sistema por causa do legado
//            log.error("[LEGADO] Falha ao replicar (não afeta o novo sistema): {}", e.getMessage());
//        }
//    }
//}
