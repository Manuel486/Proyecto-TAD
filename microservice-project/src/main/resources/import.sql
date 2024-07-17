INSERT INTO proyectos (titulo, descripcion, fecha_creacion, ciclo, usuario_id_duenio) VALUES ('Proyecto A', 'Descripción del Proyecto A', '2023-01-01', 1,1);
INSERT INTO proyectos (titulo, descripcion, fecha_creacion, ciclo, usuario_id_duenio) VALUES ('Proyecto B', 'Descripción del Proyecto B', '2023-02-01', 1,2);
INSERT INTO proyectos (titulo, descripcion, fecha_creacion, ciclo, usuario_id_duenio) VALUES ('Proyecto C', 'Descripción del Proyecto C', '2023-03-01', 2,3);

-- Insertar usuarios asociados al proyecto con ID 1
INSERT INTO usuario_proyectos (id_proyecto, usuario_id) VALUES (1, 1), (1, 2);
-- Insertar usuarios asociados al proyecto con ID 2
INSERT INTO usuario_proyectos (id_proyecto, usuario_id) VALUES (2, 2), (2, 3);
INSERT INTO usuario_proyectos (id_proyecto, usuario_id) VALUES (3, 3);