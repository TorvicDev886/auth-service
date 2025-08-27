-- Usuarios (auth-service)
CREATE TABLE IF NOT EXISTS usuarios (
  id CHAR(36) PRIMARY KEY,
  documento_identidad VARCHAR(20) NOT NULL,
  nombres VARCHAR(120) NOT NULL,
  apellidos VARCHAR(120) NOT NULL,
  fecha_nacimiento DATE NULL,
  direccion VARCHAR(200) NULL,
  telefono VARCHAR(40) NULL,
  correo_electronico VARCHAR(160) NOT NULL,
  salario_base DECIMAL(15,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uq_usuarios_doc UNIQUE (documento_identidad),
  CONSTRAINT uq_usuarios_email UNIQUE (correo_electronico)
);

-- Índices útiles para búsquedas frecuentes (además de los UNIQUE)
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios (correo_electronico);
CREATE INDEX IF NOT EXISTS idx_usuarios_doc ON usuarios (documento_identidad);
