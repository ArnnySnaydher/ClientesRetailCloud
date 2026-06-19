package com.aquispe.techretail.infrastructure.adapter.in.web;

import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.model.ClienteConExpectativa;
import com.aquispe.techretail.domain.model.KpiClientes;
import com.aquispe.techretail.domain.port.in.CreaClienteUseCase;
import com.aquispe.techretail.domain.port.in.ListaClientesUseCase;
import com.aquispe.techretail.domain.port.in.ObtenKpiClientesUseCase;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.ClienteResponse;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.CreaClienteRequest;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.KpiResponse;
import com.aquispe.techretail.infrastructure.adapter.in.web.mapper.ClienteMapper;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreaClienteUseCase creaClienteUseCase;

    @MockitoBean
    private ObtenKpiClientesUseCase obtenKpiClientesUseCase;

    @MockitoBean
    private ListaClientesUseCase listaClientesUseCase;

    @MockitoBean
    private ClienteMapper mapper;

    @Test
    void shouldCreateCliente() throws Exception {
        CreaClienteRequest request = CreaClienteRequest.builder()
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1993, 6, 18))
                .build();

        Cliente domain = Cliente.builder()
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1993, 6, 18))
                .build();

        Cliente saved = Cliente.builder()
                .id(1L)
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1993, 6, 18))
                .build();

        ClienteResponse response = ClienteResponse.builder()
                .id(1L)
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1993, 6, 18))
                .build();

        when(mapper.toDomain(any(CreaClienteRequest.class))).thenReturn(domain);
        when(creaClienteUseCase.creaCliente(any(Cliente.class))).thenReturn(saved);
        when(mapper.toResponse(any(Cliente.class))).thenReturn(response);

        mockMvc.perform(post("/api/creacliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("John"))
                .andExpect(jsonPath("$.apellido").value("Doe"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.fechaNacimiento").value("1993-06-18"));
    }

    @Test
    void shouldGetKpis() throws Exception {
        KpiClientes kpi = KpiClientes.builder().promedioEdad(35.0).desviacionEstandar(5.0).build();
        KpiResponse response = KpiResponse.builder().promedioEdad(35.0).desviacionEstandar(5.0).build();

        when(obtenKpiClientesUseCase.obtenKpiClientes()).thenReturn(kpi);
        when(mapper.toResponse(any(KpiClientes.class))).thenReturn(response);

        mockMvc.perform(get("/api/kpideclientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promedioEdad").value(35.0))
                .andExpect(jsonPath("$.desviacionEstandar").value(5.0));
    }

    @Test
    void shouldListClientes() throws Exception {
        Cliente client = Cliente.builder()
                .id(1L)
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1993, 6, 18))
                .build();
        ClienteConExpectativa item = ClienteConExpectativa.builder()
                .cliente(client)
                .fechaProbableMuerte(LocalDate.of(2073, 6, 18))
                .build();
        ClienteResponse response = ClienteResponse.builder()
                .id(1L)
                .nombre("John")
                .apellido("Doe")
                .edad(30)
                .fechaNacimiento(LocalDate.of(1993, 6, 18))
                .fechaProbableMuerte(LocalDate.of(2073, 6, 18))
                .build();

        when(listaClientesUseCase.listaClientes()).thenReturn(List.of(item));
        when(mapper.toResponse(any(ClienteConExpectativa.class))).thenReturn(response);

        mockMvc.perform(get("/api/listclientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("John"))
                .andExpect(jsonPath("$[0].fechaProbableMuerte").value("2073-06-18"));
    }
}
