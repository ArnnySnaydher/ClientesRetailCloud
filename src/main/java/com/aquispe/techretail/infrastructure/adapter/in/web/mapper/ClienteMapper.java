package com.aquispe.techretail.infrastructure.adapter.in.web.mapper;

import com.aquispe.techretail.domain.model.Cliente;
import com.aquispe.techretail.domain.model.ClienteConExpectativa;
import com.aquispe.techretail.domain.model.KpiClientes;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.ClienteResponse;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.CreaClienteRequest;
import com.aquispe.techretail.infrastructure.adapter.in.web.dto.KpiResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toDomain(CreaClienteRequest request) {
        if (request == null) {
            return null;
        }
        return Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .edad(request.getEdad())
                .fechaNacimiento(request.getFechaNacimiento())
                .build();
    }

    public ClienteResponse toResponse(Cliente domain) {
        if (domain == null) {
            return null;
        }
        return ClienteResponse.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .apellido(domain.getApellido())
                .edad(domain.getEdad())
                .fechaNacimiento(domain.getFechaNacimiento())
                .build();
    }

    public ClienteResponse toResponse(ClienteConExpectativa domainWithExpectancy) {
        if (domainWithExpectancy == null) {
            return null;
        }
        Cliente client = domainWithExpectancy.getCliente();
        return ClienteResponse.builder()
                .id(client.getId())
                .nombre(client.getNombre())
                .apellido(client.getApellido())
                .edad(client.getEdad())
                .fechaNacimiento(client.getFechaNacimiento())
                .fechaProbableMuerte(domainWithExpectancy.getFechaProbableMuerte())
                .build();
    }

    public KpiResponse toResponse(KpiClientes kpi) {
        if (kpi == null) {
            return null;
        }
        return KpiResponse.builder()
                .promedioEdad(kpi.getPromedioEdad())
                .desviacionEstandar(kpi.getDesviacionEstandar())
                .build();
    }
}
