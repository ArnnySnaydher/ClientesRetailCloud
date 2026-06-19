package com.aquispe.techretail.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteConExpectativa {
    private Cliente cliente;
    private LocalDate fechaProbableMuerte;
}
