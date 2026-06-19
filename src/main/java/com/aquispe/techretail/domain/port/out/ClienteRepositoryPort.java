package com.aquispe.techretail.domain.port.out;

import com.aquispe.techretail.domain.model.Cliente;
import java.util.List;

public interface ClienteRepositoryPort {
    Cliente save(Cliente cliente);
    List<Cliente> findAll();
}
