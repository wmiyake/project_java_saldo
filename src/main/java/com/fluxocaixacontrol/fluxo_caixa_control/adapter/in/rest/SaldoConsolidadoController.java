package com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest;

// Adicione o import correto para o seu pacote de DTO (ajuste se necessário)
import com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest.dto.SaldoConsolidadoResponse;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in.ConsultarSaldoUseCase;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.service.SaldoConsolidadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List; // Import do List adicionado

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/api/v1/saldo-consolidado")
@RequiredArgsConstructor
public class SaldoConsolidadoController {

    private final ConsultarSaldoUseCase consultarUseCase;
    private final SaldoConsolidadoService saldoService;

    @GetMapping("/{data}")
    public ResponseEntity<SaldoConsolidadoResponse> consultar(
            @PathVariable @DateTimeFormat(iso = DATE) LocalDate data) {
        return ResponseEntity.ok(
                SaldoConsolidadoResponse.from(consultarUseCase.consultarPorData(data))
        );
    }

    @GetMapping
    public ResponseEntity<List<SaldoConsolidadoResponse>> listarPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        return ResponseEntity.ok(
                saldoService.listarPorPeriodo(dataInicio, dataFim).stream()
                        .map(SaldoConsolidadoResponse::from).toList()
        );
    }

    // Dispara o job on-demand (sem esperar o agendamento de meia-noite)
    @PostMapping("/{data}/processar")
    public ResponseEntity<SaldoConsolidadoResponse> processar(
            @PathVariable @DateTimeFormat(iso = DATE) LocalDate data) {
        // CORRIGIDO: Estava chamando saldoService.from ao invés do DTO
        return ResponseEntity.ok(
                SaldoConsolidadoResponse.from(saldoService.calcularEPersistir(data))
        );
    }
}