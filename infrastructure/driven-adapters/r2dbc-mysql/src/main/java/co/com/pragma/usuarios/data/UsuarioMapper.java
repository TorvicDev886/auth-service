package co.com.pragma.usuarios.data;

import co.com.pragma.usuarios.Usuario;
import java.time.LocalDateTime;

public final class UsuarioMapper {
    private UsuarioMapper(){}

    public static UsuarioData toData(Usuario u){
        var d = new UsuarioData();
        d.setId(u.getId());
        d.setDocumentoIdentidad(u.getDocumentoIdentidad());
        d.setNombres(u.getNombres());
        d.setApellidos(u.getApellidos());
        d.setFechaNacimiento(u.getFechaNacimiento());
        d.setDireccion(u.getDireccion());
        d.setTelefono(u.getTelefono());
        d.setCorreoElectronico(u.getCorreoElectronico().value());
        d.setSalarioBase(u.getSalarioBase().value());
        d.setCreatedAt(LocalDateTime.now());
        d.setUpdatedAt(LocalDateTime.now());
        return d;
    }
}
