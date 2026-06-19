package com.aquispe.techretail.infrastructure.adapter.out.db;

import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.port.out.ClienteRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteDbAdapter implements ClienteRepositoryPort {

    private final JpaClienteRepository jpaClienteRepository;

    public ClienteDbAdapter(JpaClienteRepository jpaClienteRepository) {
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = toEntity(cliente);
        ClienteEntity savedEntity = jpaClienteRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Cliente> findAll() {
        return jpaClienteRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private ClienteEntity toEntity(Cliente domain) {
        if (domain == null) return null;
        return ClienteEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .apellido(domain.getApellido())
                .edad(domain.getEdad())
                .fechaNacimiento(domain.getFechaNacimiento())
                .build();
    }

    private Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        return Cliente.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .edad(entity.getEdad())
                .fechaNacimiento(entity.getFechaNacimiento())
                .build();
    }
}
