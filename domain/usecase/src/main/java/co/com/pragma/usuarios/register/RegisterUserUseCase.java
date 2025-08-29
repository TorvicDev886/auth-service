package co.com.pragma.usuarios.register;

import co.com.pragma.usuarios.Usuario;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface RegisterUserUseCase {
    Mono<Usuario> register(RegisterUserCommand cmd);
}
