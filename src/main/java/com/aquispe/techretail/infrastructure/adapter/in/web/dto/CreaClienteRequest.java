package com.aquispe.techretail.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreaClienteRequest {
    private String nombre;
    private String apellido;
    private Integer edad;
    private LocalDate fechaNacimiento;
}
