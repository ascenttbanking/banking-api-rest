-- resources/schema.sql
CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          correo VARCHAR(255) UNIQUE NOT NULL,
                          contrasena VARCHAR(255) NOT NULL,
                          nombre VARCHAR(100),
                          apellido VARCHAR(100)
);

CREATE TABLE cuentas (
                         id SERIAL PRIMARY KEY,
                         usuario_id INTEGER REFERENCES usuarios(id),
                         balance DECIMAL(10, 2) DEFAULT 0.00
);

CREATE TABLE habitaciones (
    
                              id SERIAL PRIMARY KEY,
                              nombre VARCHAR(100) NOT NULL,
                              descripcion TEXT,
                              precio DECIMAL(10, 2) NOT NULL,
                              disponible BOOLEAN DEFAULT TRUE
);
