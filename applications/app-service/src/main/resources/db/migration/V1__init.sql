-- Usuarios (auth-service)
CREATE TABLE IF NOT EXISTS usuarios (
  id CHAR(36) NOT NULL,
  documento_identidad VARCHAR(20) NOT NULL,
  nombres VARCHAR(120) NOT NULL,
  apellidos VARCHAR(120) NOT NULL,
  fecha_nacimiento DATE NULL,
  direccion VARCHAR(200) NULL,
  telefono VARCHAR(40) NULL,
  correo_electronico VARCHAR(160) NOT NULL,
  salario_base DECIMAL(15,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_usuarios PRIMARY KEY (id),
  CONSTRAINT uq_usuarios_doc UNIQUE (documento_identidad),
  CONSTRAINT uq_usuarios_email UNIQUE (correo_electronico),
  KEY idx_usuarios_email (correo_electronico),
  KEY idx_usuarios_doc (documento_identidad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
