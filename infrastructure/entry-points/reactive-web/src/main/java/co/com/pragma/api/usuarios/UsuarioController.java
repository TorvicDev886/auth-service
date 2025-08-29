package co.com.pragma.api.usuarios;

import co.com.pragma.api.usuarios.dto.RegisterUserRequest;
import co.com.pragma.api.usuarios.dto.UsuarioResponse;
import co.com.pragma.usuarios.register.RegisterUserCommand;
import co.com.pragma.usuarios.register.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
public class UsuarioController {

    private final RegisterUserUseCase useCase;

    public UsuarioController(RegisterUserUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public Mono<ResponseEntity<UsuarioResponse>> register(@Valid @RequestBody RegisterUserRequest r) {
        var cmd = new RegisterUserCommand(
                r.documentoIdentidad(), r.nombres(), r.apellidos(), r.fechaNacimiento(),
                r.direccion(), r.telefono(), r.correoElectronico(), r.salarioBase()
        );
        return useCase.register(cmd)
                .map(u -> {
                    var location = URI.create("/api/v1/usuarios/" + u.getId()); // Location relativo
                    var body = new UsuarioResponse(u.getId(), u.getDocumentoIdentidad(), u.getCorreoElectronico().value());
                    return ResponseEntity.created(location).body(body); // <-- 201 + Location
                });
    }

}
