-- Tipos de préstamo (solo creación + seed; NO tocar 'usuarios' aquí)
CREATE TABLE IF NOT EXISTS tipos_prestamo (
  id           CHAR(36)     NOT NULL,
  nombre       VARCHAR(100) NOT NULL,
  descripcion  VARCHAR(255) NULL,
  tasa_interes DECIMAL(5,2) NOT NULL,
  estado       VARCHAR(20)  NOT NULL DEFAULT 'ACTIVO',
  created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_tipos_prestamo PRIMARY KEY (id),
  CONSTRAINT uq_tipos_prestamo_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Seed idempotente (evita duplicados por UNIQUE(nombre))
INSERT IGNORE INTO tipos_prestamo (id, nombre, descripcion, tasa_interes, estado) VALUES
(UUID(), 'Consumo', 'Crédito de consumo', 18.50, 'ACTIVO'),
(UUID(), 'Libre inversión', 'Crédito de libre inversión', 20.00, 'ACTIVO'),
(UUID(), 'Educativo', 'Crédito educativo', 12.00, 'ACTIVO');
