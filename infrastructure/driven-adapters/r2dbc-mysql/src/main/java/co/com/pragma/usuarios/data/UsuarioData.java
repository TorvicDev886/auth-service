package co.com.pragma.usuarios.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("usuarios")
public class UsuarioData {
    @Id private String id;
    @Column("documento_identidad") private String documentoIdentidad;
    @Column("nombres") private String nombres;
    @Column("apellidos") private String apellidos;
    @Column("fecha_nacimiento") private LocalDate fechaNacimiento;
    @Column("direccion") private String direccion;
    @Column("telefono") private String telefono;
    @Column("correo_electronico") private String correoElectronico;
    @Column("salario_base") private BigDecimal salarioBase;
    @Column("created_at") private LocalDateTime createdAt;
    @Column("updated_at") private LocalDateTime updatedAt;
}
