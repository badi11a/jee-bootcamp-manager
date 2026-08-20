-- Script de creación de la base de datos y tablas para Bootcamp Manager

CREATE DATABASE IF NOT EXISTS bootcamp_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bootcamp_manager;

-- Tabla estudiante
CREATE TABLE IF NOT EXISTS estudiante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT 1
);

INSERT INTO estudiante (rut, nombre, email, activo) VALUES
('12345678-9', 'Juan Pérez', 'juan.perez@email.com', 1),
('98765432-1', 'María López', 'maria.lopez@email.com', 1),
('11222333-4', 'Pedro González', 'pedro.gonzalez@email.com', 1);

-- Tabla curso
CREATE TABLE IF NOT EXISTS curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

INSERT INTO curso (nombre, descripcion) VALUES
('Java Básico', 'Introducción a Java y programación orientada a objetos'),
('Web Avanzado', 'Desarrollo web con Java EE y frameworks modernos');

-- Tabla inscripcion
CREATE TABLE IF NOT EXISTS inscripcion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT NOT NULL,
    curso_id INT NOT NULL,
    fecha_inscripcion DATE NOT NULL,
    FOREIGN KEY (estudiante_id) REFERENCES estudiante(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

INSERT INTO inscripcion (estudiante_id, curso_id, fecha_inscripcion) VALUES
(1, 1, '2026-01-10'),
(2, 2, '2026-02-15');
