package com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest;

import com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest.dto.LancamentoRequest;
import com.fluxocaixacontrol.fluxo_caixa_control.adapter.in.rest.dto.LancamentoResponse;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in.ConsultarLancamentoUseCase;
import com.fluxocaixacontrol.fluxo_caixa_control.domain.port.in.RegistrarLancamentoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/api/v1/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final RegistrarLancamentoUseCase registrarUseCase;
    private final ConsultarLancamentoUseCase consultarUseCase;

    // POST — registrar
    @PostMapping
    public ResponseEntity<LancamentoResponse> registrar(
            @Valid @RequestBody LancamentoRequest request,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {
        try {
            var l = registrarUseCase.registrar(request.tipo(), request.valor(),
                    request.descricao(), request.dataHora(), idempotencyKey);
            return ResponseEntity.status(201).body(LancamentoResponse.from(l));

        } catch (RegistrarLancamentoUseCase.LancamentoDuplicadoException e) {
            // Idempotência: retorna o existente com 200
            return ResponseEntity.ok(LancamentoResponse.from(e.getLancamentoExistente()));
        }
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<LancamentoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(
                LancamentoResponse.from(consultarUseCase.buscarPorId(id))
        );
    }

    // GET por data
    @GetMapping
    public ResponseEntity<List<LancamentoResponse>> listarPorData(
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate data) {
        return ResponseEntity.ok(
                consultarUseCase.listarPorData(data).stream()
                        .map(LancamentoResponse::from).toList()
        );
    }

    // PUT/PATCH/DELETE → 405 (imutabilidade)
    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> putBloqueado(@PathVariable UUID id) {
        return ResponseEntity.status(405)
                .body(ErrorResponse.of("Lançamentos são imutáveis", "LANCAMENTO_IMUTAVEL"));
    }
}
