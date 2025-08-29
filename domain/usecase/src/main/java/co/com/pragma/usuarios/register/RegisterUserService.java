package co.com.pragma.usuarios.register;

import co.com.pragma.shared.DomainException;
import co.com.pragma.usuarios.Usuario;
import co.com.pragma.usuarios.ports.out.UsuarioRepository;
import reactor.core.publisher.Mono;

public class RegisterUserService implements RegisterUserUseCase {

    private final UsuarioRepository repo;

    public RegisterUserService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public Mono<Usuario> register(RegisterUserCommand cmd) {
        if (cmd == null) return Mono.error(new DomainException("command requerido"));

        var draft = new Usuario.Draft(
                cmd.documentoIdentidad(), cmd.nombres(), cmd.apellidos(),
                cmd.fechaNacimiento(), cmd.direccion(), cmd.telefono(),
                cmd.correoElectronico(), cmd.salarioBase()
        );
        var usuario = Usuario.create(draft);

        return repo.existsByEmail(usuario.getCorreoElectronico().value())
                .flatMap(exists -> exists
                        ? Mono.<Void>error(new DomainException("correo_electronico ya registrado"))
                        : Mono.empty())
                .then(repo.existsByDocumento(usuario.getDocumentoIdentidad())
                        .flatMap(exists -> exists
                                ? Mono.<Void>error(new DomainException("documento_identidad ya registrado"))
                                : Mono.empty()))
                .then(repo.create(usuario));
    }
}
