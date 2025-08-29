package co.com.pragma.app.usuarios;

import co.com.pragma.usuarios.Usuario;
import co.com.pragma.usuarios.register.RegisterUserCommand;
import co.com.pragma.usuarios.register.RegisterUserUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class RegisterUserApplicationService {

    private static final Logger log = LoggerFactory.getLogger(RegisterUserApplicationService.class);
    private final RegisterUserUseCase useCase;

    public RegisterUserApplicationService(RegisterUserUseCase useCase) {
        this.useCase = useCase;
    }

    @Transactional
    public Mono<Usuario> register(RegisterUserCommand cmd) {
        log.info("[HU1] registrar doc={}, email={}", cmd.documentoIdentidad(), cmd.correoElectronico());
        return useCase.register(cmd);
    }
}
