package com.aquispe.techretail.domain.port.in;

import com.aquispe.techretail.domain.model.ClienteConExpectativa;
import java.util.List;

public interface ListaClientesUseCase {
    List<ClienteConExpectativa> listaClientes();
}
