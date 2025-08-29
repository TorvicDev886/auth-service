package co.com.pragma.usuarios.data;

import co.com.pragma.shared.DomainException;
import co.com.pragma.usuarios.Usuario;
import co.com.pragma.usuarios.ports.out.UsuarioRepository;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioReactiveRepository repo;   // exists*
    private final R2dbcEntityTemplate template;     // insert/update explícitos

    public UsuarioRepositoryAdapter(UsuarioReactiveRepository repo,
                                    R2dbcEntityTemplate template) {
        this.repo = repo;
        this.template = template;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repo.existsByCorreoElectronico(email);
    }

    @Override
    public Mono<Boolean> existsByDocumento(String doc) {
        return repo.existsByDocumentoIdentidad(doc);
    }

    @Override
    public Mono<Usuario> create(Usuario u) {
        var data = UsuarioMapper.toData(u);

        // INSERT explícito: si viola UNIQUE, capturamos y lo mapeamos al dominio.
        return template.insert(UsuarioData.class)
                .using(data)
                .map(saved -> u)
                .onErrorMap(this::isUniqueViolation,
                        ex -> new DomainException("usuario_duplicado: correo_electronico o documento_identidad ya registrado"));
    }

    @Override
    public Mono<Usuario> update(Usuario u) {
        var data = UsuarioMapper.toData(u);
        if (data.getId() == null) {
            return Mono.error(new IllegalArgumentException("id requerido para update"));
        }

        // UPDATE por id: si no existe la fila, Spring suele lanzar un error con el texto “does not exist”.
        return template.update(data)
                .map(saved -> u)
                .onErrorMap(this::isUniqueViolation,
                        ex -> new DomainException("usuario_duplicado: correo_electronico o documento_identidad ya registrado"))
                .onErrorMap(this::isNotFoundOnUpdate,
                        ex -> new DomainException("usuario_no_existe"));
    }

    // --- Helpers de traducción de errores ---

    /** Detecta violaciones de UNIQUE/duplicados (MySQL 1062, “Duplicate entry”, etc.). */
    private boolean isUniqueViolation(Throwable t) {
        Throwable c = t;
        while (c != null) {
            if (c instanceof DuplicateKeyException) return true;
            if (c instanceof DataIntegrityViolationException) return true;
            if (c instanceof R2dbcDataIntegrityViolationException) return true;

            String m = c.getMessage();
            if (m != null) {
                String msg = m.toLowerCase();
                if (msg.contains("duplicate entry") || msg.contains("duplicate key")
                        || msg.contains("unique") || msg.contains("1062")) {
                    return true;
                }
            }
            c = c.getCause();
        }
        return false;
    }

    /** Detecta el caso de update sobre fila inexistente. */
    private boolean isNotFoundOnUpdate(Throwable t) {
        Throwable c = t;
        while (c != null) {
            String m = c.getMessage();
            if (m != null && m.toLowerCase().contains("does not exist")) {
                return true;
            }
            c = c.getCause();
        }
        return false;
    }
}