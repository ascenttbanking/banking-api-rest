-- resources/data.sql
INSERT INTO usuarios (correo, contrasena, nombre, apellido) VALUES
                                                                ('usuario1@example.com', 'contrasena1', 'Nombre1', 'Apellido1'),
                                                                ('usuario2@example.com', 'contrasena2', 'Nombre2', 'Apellido2');

INSERT INTO habitaciones (nombre, descripcion, precio, disponible) VALUES
                                                                       ('Habitación 101', 'Habitación con vista al mar', 100.00, TRUE),
                                                                       ('Habitación 102', 'Habitación con balcón', 80.00, TRUE);
