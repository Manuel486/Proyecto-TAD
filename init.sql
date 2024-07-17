-- Eliminar bases de datos si existen
DROP DATABASE IF EXISTS projectsDb;
DROP DATABASE IF EXISTS usersDb;
DROP DATABASE IF EXISTS annotationsDb;

-- Crear las bases de datos
CREATE DATABASE IF NOT EXISTS projectsDb;
CREATE DATABASE IF NOT EXISTS usersDb;
CREATE DATABASE IF NOT EXISTS annotationsDb;