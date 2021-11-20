/**
 * Author:  Isabel
 * Created: 01 nov. 2021
 */

INSERT INTO `editoriales`(`desactivado`, `nombre_editorial`) VALUES
(0, "Grupo Planeta"),
(0, "Planeta Junior"),
(0, "Debolsillo"),
(0, "Martinez Roca"),
(0, "S.A. Ediciones"),
(0, "S.A. Marcombo");


INSERT INTO `generos`(`desactivado`, `nombre`) VALUES
(0, "Terror y Misterio"),
(0, "Fantasía"),
(0, "Ciencia-Ficción"),
(0, "Infantil"),
(0, "Policíaca"),
(0, "Romántica"),
(0, "Informática"),
(0, "Aventuras");

INSERT INTO `autores`(`apellidos`, `biografia`, `desactivado`, `nombre`) VALUES
("King", "Autor de libros de suspense, misterio y terror", 0, "Stephen"),
("Fernández", "Autora anónima. Conocida por su saga Memorias de una salvaje", 0, "Bebi"),
("Jiménez Marín y Pérez Montes", "Autores de libros de informática", 0, "Alfonso y Francisco Manuel"),
("Tomás y Tirado", "Autores de libros de informática", 0, "Jesús y Beatriz"),
("Gomez Jurado", "Autor muy conocido por su trilogía Reina Roja", 0, "Juan");

INSERT INTO `libros`(`desactivado`, `idioma`, `isbn`, `numero_valoraciones`, `titulo`, `uri_portada`, `valoracion_libro`, `id_autor`, `id_editorial`, `id_genero`) VALUES
(0, "Español", "23742394723", 4, "Carrie", "https://images-na.ssl-images-amazon.com/images/I/41MAkt8G-YL._SX248_BO1,204,203,200_.jpg", 4.9, 1, 3, 1),
(0, "Español", "54545648884", 2, "Cujo", "https://images-na.ssl-images-amazon.com/images/I/81LxJZVmmJL.jpg", 5, 1, 3, 1),
(0, "Español", "76468786546", 6, "Memorias de una salvaje", "https://images-na.ssl-images-amazon.com/images/I/91PsL0Zw2IL.jpg", 4.6, 2, 4, 5),
(0, "Español", "54654654545", 1, "Aprende a programar con java", "https://images-na.ssl-images-amazon.com/images/I/91kIPBbe+VL.jpg", 4, 3, 4, 7),
(0, "Español", "65465465446", 6, "El gran libro de Android", "https://images-na.ssl-images-amazon.com/images/I/71Wplr0+DkL.jpg", 3.8, 4, 4, 7),
(0, "Español", "87646546413", 8, "Reina Roja", "https://images-na.ssl-images-amazon.com/images/I/71GC9IBeQjL.jpg", 5, 5, 5, 5),
(0, "Español", "65465465446", 8, "Loba Negra", "https://imagessl7.casadellibro.com/a/l/t5/97/9788466666497.jpg", 4.5, 5, 5, 5),
(0, "Español", "65465465446", 7, "Rey Blanco", "https://images-na.ssl-images-amazon.com/images/I/71zNu2BR8XL.jpg", 4.8, 5, 5, 5);

INSERT INTO `autor_libro`(`id_autor`, `id_libro`) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(5, 7),
(5, 8);

insert into usuarios values (1, "Perez", "Zgz", "calle claves",30.45,12.54, "zgz@hibes.com", "Juan", "666666666","uri1",3.6);
insert into usuarios values (2, "sanchez", "Zgz", "calle azucenas",30.45,12.54, "zrz@hibeus.com", "Luis", "7777777","uri2",3.5);
insert into usuarios values (3, "williams", "Zgz", "Plaza Decilias",30.45,12.54, "ztz@hibrus.com", "manolo ", "88888888","uri3",4.6);
insert into usuarios values (4, "Lopez", "Zgz", "c\Decilias",30.45,12.54, "zyz@hberus.com", "Lolo no manolo", "99999999","uri4",2.6);

INSERT INTO `preferencias`(`id_genero`, `id_usuario`) VALUES
(1, 1),
(5, 1),
(6, 2),
(7, 3),
(2, 4),
(1, 2),
(1, 4),
(3, 3);

INSERT INTO `usuario_libro`(`desactivado`, `estado_conservacion`, `estado_prestamo`, `quiero_tengo`, `id_libro`, `id_usuario`) VALUES
(0, "Buen estado", "Disponible", "Tengo", 1, 1),
(0, "Un poco deteriorado", "Disponible", "Tengo", 2, 1),
(0, "Buen estado", "Ocupado", "Quiero", 6, 2),
(0, "Deteriorado", "Disponible", "Tengo", 3, 1);

INSERT INTO `peticiones`(`aceptacion`, `pendiente_tratar`, `id_usuario_libro`, `id_usuario_solicitante`) VALUES
(null, 1, 1, 2),
(null, 0, 1, 3),
(null, 0, 2, 4),
(null, 0, 2, 1),
(null, 1, 1, 3),
(null, 1, 1, 4),
(null, 1, 2, 1);

INSERT INTO `intercambios`(`fecha_devolucion`, `fecha_prestamo`, `id_usuario_libro_prestador`, `id_usuario_libro_prestatario`) VALUES
("2021-12-15", "2021-11-15", 1, 2),
("2021-12-08", "2021-11-05", 1, 3),
("2021-12-20", "2021-11-13", 1, 4),
("2021-12-26", "2021-11-30", 2, 1);


