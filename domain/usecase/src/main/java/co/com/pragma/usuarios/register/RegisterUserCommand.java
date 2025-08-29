package co.com.pragma.usuarios.register;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterUserCommand(
        String documentoIdentidad,
        String nombres,
        String apellidos,
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        String correoElectronico,
        BigDecimal salarioBase
) {}
