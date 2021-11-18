/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Usuario
 * Created: 16 sept. 2021
 */

insert into editoriales values (1,"Alfaguara");
insert into editoriales values (2,"Anaya");
insert into editoriales values (3,"Barco de vapor");
insert into editoriales values (4,"Titum mas");


insert into generos values (1, "Terror");
insert into generos values (2, "Sci-Fi");
insert into generos values (3, "Manuales Tecnicos");
insert into generos values (4, "Diccionarios/Enciclopedias");
insert into generos values (5, "Cuentos infantiles");
insert into generos values (6, "Comedia");
insert into generos values (7, "Misterio");
insert into generos values (8, "Ciencia Ficción");
insert into generos values (9, "Informática");
insert into generos values (10, "Romántica");
insert into generos values (11, "Relatos");
insert into generos values (12, "Novela Negra");
insert into generos values (13, "Historia");

insert into autores values (1,"Perez Reverte","Autor muy chulo","Arturo");
insert into autores values (2,"Dahl","Autor de cuentos","Ronalh");
insert into autores values (3,"Hemanos Green","Autor de cuentos","");
insert into autores values (4,"Ojuelos gomez","Autor de tratados derecho descanso","Francisco jose");
insert into autores values (5,"Rosselló i Boeres","Autor de tratados derecho descanso","Bartomeu");
insert into autores values (6,"García Virto","Autora muy buena en el género terror y misterio","Gemma");
insert into autores values (7,"Gómez Jurado","Autor de la famosa trilogía Reina Roja","Juan");
insert into autores values (8,"King","Gran autor de libros de misterio","Stephen");

insert into libros (id, idioma, isbn,titulo, uri_portada,id_genero, id_editorial, valoracion_libro) values(1, "espanyol", "444441", "El capitan alatriste", "http://www.google.com",2,1, 4.6);
insert into libros (id, idioma, isbn,titulo, uri_portada,id_genero, id_editorial, valoracion_libro) values(2, "espanyol", "111112", "la reina del sur", "http://www.google.com",2,3, 3.2);
insert into libros (id, idioma, isbn,titulo, uri_portada,id_genero, id_editorial, valoracion_libro) values(3, "espanyol", "222223", "harry y la fabrica de chocolate", "http://www.yahoo.com",5,4, 2 );
insert into libros (id, idioma, isbn,titulo, uri_portada,id_genero, id_editorial, valoracion_libro) values(4, "espanyol", "77774", "Matilda", "http://www.yahoo.com",5,2, 3.5);
insert into libros (id, idioma, isbn,titulo, uri_portada,id_genero, id_editorial, valoracion_libro) values(5, "espanyol", "84213215", "las brujas", "http://www.yahoo.com",5,3, 2.1);
insert into libros (id, idioma, isbn,titulo, uri_portada,id_genero, id_editorial, valoracion_libro) values(6, "espanyol", "84213215", "derecho al silencio", "http://www.yahoo.com",3,4, 4.1);


insert into libro_autor (id,id_autor,id_libro) values (1,1,1); # reverte - capital alatriste
insert into libro_autor (id,id_autor,id_libro) values (2,1,2); # reverte - la reina del sur
insert into libro_autor (id,id_autor,id_libro) values (3,2,3); # dahl - harry y la fabrica
insert into libro_autor (id,id_autor,id_libro) values (4,2,4); # dahl - matilda
insert into libro_autor (id,id_autor,id_libro) values (5,2,5); # dahl - las brujas
insert into libro_autor (id,id_autor,id_libro) values (6,4,6); # Francisco José Ojuelos Gómez - derecho al silencio
insert into libro_autor (id,id_autor,id_libro) values (7,5,6); # Bartomeu Rosselló i Boeres - derecho al silencio


insert into usuarios values (1, "Perez", "Zgz", "calle claves",30.45,12.54, "zgz@hibes.com", "Juan", "666666666","uri1",3.6);
insert into usuarios values (2, "sanchez", "Zgz", "calle azucenas",30.45,12.54, "zrz@hibeus.com", "Luis", "7777777","uri2",3.5);
insert into usuarios values (3, "williams", "Zgz", "Plaza Decilias",30.45,12.54, "ztz@hibrus.com", "manolo ", "88888888","uri3",4.6);
insert into usuarios values (4, "Lopez", "Zgz", "c\Decilias",30.45,12.54, "zyz@hberus.com", "Lolo no manolo", "99999999","uri4",2.6);

insert into preferencias (id,id_genero,id_usuario) values (1,1,1);
insert into preferencias (id,id_genero,id_usuario) values (2,2,1);
insert into preferencias (id,id_genero,id_usuario) values (3,3,1);
insert into preferencias (id,id_genero,id_usuario) values (4,1,2);
insert into preferencias (id,id_genero,id_usuario) values (5,2,2);
insert into preferencias (id,id_genero,id_usuario) values (6,3,3);
insert into preferencias (id,id_genero,id_usuario) values (7,4,4);

insert into usuario_libro (id,estado_conservacion,estado_prestamo,quiero_tengo,id_libro,id_usuario) values (1,"Buen Estado","Libre","Quiero",1,1);
insert into usuario_libro (id,estado_conservacion,estado_prestamo,quiero_tengo,id_libro,id_usuario) values (2,"Buen Estado","Ocupado","Tengo",1,4);
insert into usuario_libro (id,estado_conservacion,estado_prestamo,quiero_tengo,id_libro,id_usuario) values (3,"Buen Estado","Libre","Tengo",3,2);
insert into usuario_libro (id,estado_conservacion,estado_prestamo,quiero_tengo,id_libro,id_usuario) values (4,"Buen Estado","Libre","Tengo",3,4);
insert into usuario_libro (id,estado_conservacion,estado_prestamo,quiero_tengo,id_libro,id_usuario) values (5,"Deteriorado","Ocupado","Tengo",4,1);
insert into usuario_libro (id,estado_conservacion,estado_prestamo,quiero_tengo,id_libro,id_usuario) values (6,"Bastante Roto","Ocupado","Tengo",5,3);

insert into peticiones (id,aceptacion, id_usuario_libro,id_usuario_solicitante, pendiente_tratar )  values (1,null,1,2,true);
insert into peticiones (id,aceptacion, id_usuario_libro,id_usuario_solicitante, pendiente_tratar )  values (2,null,1,2,false);
insert into peticiones (id,aceptacion, id_usuario_libro,id_usuario_solicitante, pendiente_tratar )  values (3,null,2,1,true);
insert into peticiones (id,aceptacion, id_usuario_libro,id_usuario_solicitante, pendiente_tratar )  values (4,null,2,3,true);
insert into peticiones (id,aceptacion, id_usuario_libro,id_usuario_solicitante, pendiente_tratar )  values (5,null,3,4,true);


insert into intercambios values (1,"2021-09-16","2021-09-30",1,2);
insert into intercambios values (2,"2021-09-18","2021-10-12",2,1);
insert into intercambios values (3,"2021-09-10","2021-10-11",3,4);
insert into intercambios values (4,"2021-09-10","2021-10-11",4,3);


