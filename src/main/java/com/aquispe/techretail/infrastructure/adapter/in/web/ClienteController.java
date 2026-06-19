package com.aquispe.techretail.infrastructure.adapter.in.web;

import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.model.KpiClientes;
import com.aquispe.techretail.domain.port.in.CreaClienteUseCase;
import com.aquispe.techretail.domain.port.in.ListaClientesUseCase;
import com.aquispe.techretail.domain.port.in.ObtenKpiClientesUseCase;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.ClienteResponse;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.CreaClienteRequest;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.KpiResponse;
import com.aquispe.techretail.infrastructure.adapter.in.web.mapper.ClienteMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClienteController {

    private final CreaClienteUseCase creaClienteUseCase;
    private final ObtenKpiClientesUseCase obtenKpiClientesUseCase;
    private final ListaClientesUseCase listaClientesUseCase;
    private final ClienteMapper mapper;

    @PostMapping("/creacliente")
    public ResponseEntity<ClienteResponse> creaCliente(@RequestBody CreaClienteRequest request) {
        Cliente domain = mapper.toDomain(request);
        Cliente saved = creaClienteUseCase.creaCliente(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping("/kpideclientes")
    public ResponseEntity<KpiResponse> obtenKpiClientes() {
        KpiClientes kpi = obtenKpiClientesUseCase.obtenKpiClientes();
        return ResponseEntity.ok(mapper.toResponse(kpi));
    }

    @GetMapping("/listclientes")
    public ResponseEntity<List<ClienteResponse>> listaClientes() {
        List<ClienteResponse> response = listaClientesUseCase.listaClientes().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
