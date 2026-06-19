package com.aquispe.techretail.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiClientes {
    private Double promedioEdad;
    private Double desviacionEstandar;
}
