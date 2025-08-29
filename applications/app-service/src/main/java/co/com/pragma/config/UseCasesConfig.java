package co.com.pragma.config;

import co.com.pragma.usuarios.ports.out.UsuarioRepository;
import co.com.pragma.usuarios.register.RegisterUserService;
import co.com.pragma.usuarios.register.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {
        @Bean
        public RegisterUserUseCase registerUserUseCase(UsuarioRepository repo) {
                return new RegisterUserService(repo);
        }
}
