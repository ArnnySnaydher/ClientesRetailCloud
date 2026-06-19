package com.aquispe.techretail.application.service;

import com.aquispe.techretail.application.util.LifeExpectancyCalculator;
import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.model.ClienteConExpectativa;
import com.aquispe.techretail.domain.model.KpiClientes;
import com.aquispe.techretail.domain.port.out.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;

    @Mock
    private LifeExpectancyCalculator lifeExpectancyCalculator;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepositoryPort, lifeExpectancyCalculator);
    }

    @Test
    void shouldCreateCliente() {
        Cliente input = Cliente.builder().nombre("John").build();
        Cliente expected = Cliente.builder().id(1L).nombre("John").build();
        when(clienteRepositoryPort.save(input)).thenReturn(expected);

        Cliente result = clienteService.creaCliente(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getNombre());
        verify(clienteRepositoryPort, times(1)).save(input);
    }

    @Test
    void shouldReturnEmptyKpiWhenNoClients() {
        when(clienteRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        KpiClientes result = clienteService.obtenKpiClientes();

        assertEquals(0.0, result.getPromedioEdad());
        assertEquals(0.0, result.getDesviacionEstandar());
    }

    @Test
    void shouldCalculateKpisCorrectly() {
        List<Cliente> clients = List.of(
                Cliente.builder().edad(20).build(),
                Cliente.builder().edad(30).build(),
                Cliente.builder().edad(40).build()
        );
        when(clienteRepositoryPort.findAll()).thenReturn(clients);

        KpiClientes result = clienteService.obtenKpiClientes();

        assertEquals(30.0, result.getPromedioEdad(), 0.001);
        assertEquals(8.165, result.getDesviacionEstandar(), 0.001);
    }

    @Test
    void shouldReturnListWithExpectancy() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        LocalDate deathDate = LocalDate.of(2070, 1, 1);
        List<Cliente> clients = List.of(
                Cliente.builder().id(1L).nombre("John").fechaNacimiento(birthDate).build()
        );
        when(clienteRepositoryPort.findAll()).thenReturn(clients);
        when(lifeExpectancyCalculator.calculateExpectedDeathDate(birthDate)).thenReturn(deathDate);

        List<ClienteConExpectativa> result = clienteService.listaClientes();

        assertEquals(1, result.size());
        assertEquals(deathDate, result.getFirst().getFechaProbableMuerte());
        assertEquals("John", result.getFirst().getCliente().getNombre());
    }
}
