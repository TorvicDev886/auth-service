package co.com.pragma.usuarios;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.UUID;
import java.math.BigDecimal;

import co.com.pragma.shared.DomainException;
import co.com.pragma.usuarios.vo.Email;
import co.com.pragma.usuarios.vo.SalarioBase;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Usuario {

    // Datos crudos (desde DTO)
    public static record Draft(
            String documentoIdentidad,
            String nombres,
            String apellidos,
            LocalDate fechaNacimiento,
            String direccion,
            String telefono,
            String correoElectronico,
            BigDecimal salarioBase
    ) {}

    private static record Core(
            String documentoIdentidad,
            String nombres,
            String apellidos,
            LocalDate fechaNacimiento,
            String direccion,
            String telefono,
            Email correoElectronico,
            SalarioBase salarioBase
    ) {}

    @EqualsAndHashCode.Include
    private final String id;
    private final String documentoIdentidad;
    private final String nombres;
    private final String apellidos;
    private final LocalDate fechaNacimiento;
    private final String direccion;
    private final String telefono;
    private final Email correoElectronico;
    private final SalarioBase salarioBase;


    private Usuario(String id, Core core) {
        this.id = id;
        this.documentoIdentidad = core.documentoIdentidad();
        this.nombres = core.nombres();
        this.apellidos = core.apellidos();
        this.fechaNacimiento = core.fechaNacimiento();
        this.direccion = core.direccion();
        this.telefono = core.telefono();
        this.correoElectronico = core.correoElectronico();
        this.salarioBase = core.salarioBase();
    }


    public static Usuario create(Draft draft) {
        if (isBlank(draft.documentoIdentidad())) throw new DomainException("documento_identidad requerido");
        if (isBlank(draft.nombres())) throw new DomainException("nombres requeridos");
        if (isBlank(draft.apellidos())) throw new DomainException("apellidos requeridos");

        Email email = Email.of(draft.correoElectronico());
        SalarioBase salario = SalarioBase.of(draft.salarioBase());

        Core core = new Core(
                draft.documentoIdentidad().trim(),
                draft.nombres().trim(),
                draft.apellidos().trim(),
                draft.fechaNacimiento(),
                trimOrNull(draft.direccion()),
                trimOrNull(draft.telefono()),
                email,
                salario
        );
        return new Usuario(UUID.randomUUID().toString(), core);
    }

    private static boolean isBlank(String s){ return s == null || s.isBlank(); }
    private static String trimOrNull(String s){ return s == null ? null : s.trim(); }
}