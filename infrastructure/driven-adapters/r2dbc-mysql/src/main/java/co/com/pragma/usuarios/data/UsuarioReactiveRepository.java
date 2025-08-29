package co.com.pragma.usuarios.data;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UsuarioReactiveRepository extends R2dbcRepository<UsuarioData, String> {
    Mono<Boolean> existsByCorreoElectronico(String correoElectronico);
    Mono<Boolean> existsByDocumentoIdentidad(String documentoIdentidad);
}

