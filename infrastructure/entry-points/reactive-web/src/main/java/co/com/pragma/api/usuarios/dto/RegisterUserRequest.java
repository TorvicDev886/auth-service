package co.com.pragma.api.usuarios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterUserRequest(
        @NotBlank @JsonProperty("documento_identidad") String documentoIdentidad,
        @NotBlank String nombres,
        @NotBlank String apellidos,
        @JsonProperty("fecha_nacimiento") LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        @NotBlank @Email @JsonProperty("correo_electronico") String correoElectronico,
        @NotNull @DecimalMin("0.0") @DecimalMax("15000000.0") @JsonProperty("salario_base") BigDecimal salarioBase
) {}

