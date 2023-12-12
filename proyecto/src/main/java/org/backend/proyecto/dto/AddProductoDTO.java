package org.backend.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductoDTO {
    @NotNull
    private Long id;

    @NotNull
    private Integer cantidad;
}
