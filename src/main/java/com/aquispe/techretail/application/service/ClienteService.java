package com.aquispe.techretail.application.service;

import com.aquispe.techretail.application.util.LifeExpectancyCalculator;
import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.model.ClienteConExpectativa;
import com.aquispe.techretail.domain.model.KpiClientes;
import com.aquispe.techretail.domain.port.in.CreaClienteUseCase;
import com.aquispe.techretail.domain.port.in.ListaClientesUseCase;
import com.aquispe.techretail.domain.port.in.ObtenKpiClientesUseCase;
import com.aquispe.techretail.domain.port.out.ClienteRepositoryPort;

import java.time.LocalDate;
import java.util.List;

public class ClienteService implements CreaClienteUseCase, ObtenKpiClientesUseCase, ListaClientesUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final LifeExpectancyCalculator lifeExpectancyCalculator;

    public ClienteService(ClienteRepositoryPort clienteRepositoryPort, LifeExpectancyCalculator lifeExpectancyCalculator) {
        this.clienteRepositoryPort = clienteRepositoryPort;
        this.lifeExpectancyCalculator = lifeExpectancyCalculator;
    }

    @Override
    public Cliente creaCliente(Cliente cliente) {
        return clienteRepositoryPort.save(cliente);
    }

    @Override
    public KpiClientes obtenKpiClientes() {
        List<Cliente> clientes = clienteRepositoryPort.findAll();
        if (clientes.isEmpty()) {
            return new KpiClientes(0.0, 0.0);
        }

        double promedio = clientes.stream()
                .mapToInt(Cliente::getEdad)
                .average()
                .orElse(0.0);

        double sumVariance = clientes.stream()
                .mapToDouble(c -> Math.pow(c.getEdad() - promedio, 2))
                .sum();

        double desviacionEstandar = Math.sqrt(sumVariance / clientes.size());

        return new KpiClientes(promedio, desviacionEstandar);
    }

    @Override
    public List<ClienteConExpectativa> listaClientes() {
        return clienteRepositoryPort.findAll().stream()
                .map(cliente -> {
                    LocalDate fechaMuerte = lifeExpectancyCalculator.calculateExpectedDeathDate(cliente.getFechaNacimiento());
                    return new ClienteConExpectativa(cliente, fechaMuerte);
                })
                .toList();
    }
}
