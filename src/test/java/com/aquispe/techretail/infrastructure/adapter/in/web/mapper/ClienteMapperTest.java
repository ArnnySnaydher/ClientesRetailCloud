package com.aquispe.techretail.infrastructure.adapter.in.web.mapper;

import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.model.ClienteConExpectativa;
import com.aquispe.techretail.domain.model.KpiClientes;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.ClienteResponse;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.CreaClienteRequest;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.KpiResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    private final ClienteMapper mapper = new ClienteMapper();

    @Test
    void shouldMapCreaClienteRequestToDomain() {
        CreaClienteRequest request = CreaClienteRequest.builder()
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        Cliente domain = mapper.toDomain(request);

        assertNotNull(domain);
        assertEquals("John", domain.getNombre());
        assertEquals("Doe", domain.getApellido());
        assertEquals(30, domain.getEdad());
        assertEquals(LocalDate.of(1990, 1, 1), domain.getFechaNacimiento());
    }

    @Test
    void shouldMapClienteToResponse() {
        Cliente domain = Cliente.builder()
                .id(1L)
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        ClienteResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getNombre());
        assertEquals("Doe", response.getApellido());
        assertEquals(30, response.getEdad());
        assertEquals(LocalDate.of(1990, 1, 1), response.getFechaNacimiento());
        assertNull(response.getFechaProbableMuerte());
    }

    @Test
    void shouldMapClienteConExpectativaToResponse() {
        Cliente client = Cliente.builder()
                .id(1L)
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();
        ClienteConExpectativa domain = ClienteConExpectativa.builder()
                .cliente(client)
                .fechaProbableMuerte(LocalDate.of(2070, 1, 1))
                .build();

        ClienteResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getNombre());
        assertEquals("Doe", response.getApellido());
        assertEquals(30, response.getEdad());
        assertEquals(LocalDate.of(1990, 1, 1), response.getFechaNacimiento());
        assertEquals(LocalDate.of(2070, 1, 1), response.getFechaProbableMuerte());
    }

    @Test
    void shouldMapKpiClientesToResponse() {
        KpiClientes kpi = KpiClientes.builder()
                .promedioEdad(33.5)
                .desviacionEstandar(4.2)
                .build();

        KpiResponse response = mapper.toResponse(kpi);

        assertNotNull(response);
        assertEquals(33.5, response.getPromedioEdad());
        assertEquals(4.2, response.getDesviacionEstandar());
    }
}
