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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clientes", description = "Endpoints para la gestión y análisis de clientes")
public class ClienteController {

    private final CreaClienteUseCase creaClienteUseCase;
    private final ObtenKpiClientesUseCase obtenKpiClientesUseCase;
    private final ListaClientesUseCase listaClientesUseCase;
    private final ClienteMapper mapper;

    @PostMapping("/creacliente")
    @Operation(
        summary = "Crear un nuevo cliente",
        description = "Guarda un nuevo cliente con su nombre, apellido, edad y fecha de nacimiento.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )
    public ResponseEntity<ClienteResponse> creaCliente(@Valid @RequestBody CreaClienteRequest request) {
        Cliente domain = mapper.toDomain(request);
        Cliente saved = creaClienteUseCase.creaCliente(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping("/kpideclientes")
    @Operation(
        summary = "Obtener KPIs de clientes",
        description = "Devuelve el promedio de edad y la desviación estándar de la edad de todos los clientes.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
        }
    )
    public ResponseEntity<KpiResponse> obtenKpiClientes() {
        KpiClientes kpi = obtenKpiClientesUseCase.obtenKpiClientes();
        return ResponseEntity.ok(mapper.toResponse(kpi));
    }

    @GetMapping("/listclientes")
    @Operation(
        summary = "Listar todos los clientes",
        description = "Retorna una lista de todos los clientes registrados, incluyendo su fecha probable de muerte.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
        }
    )
    public ResponseEntity<List<ClienteResponse>> listaClientes() {
        List<ClienteResponse> response = listaClientesUseCase.listaClientes().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
