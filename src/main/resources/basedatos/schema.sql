
create table editoriales (
    id int auto_increment primary key,
    nombre_editorial Varchar(100)
);


create table libro_autor (
id int auto_increment primary key,
id_libro int,
id_autor int,
CONSTRAINT FK_libro_autor_libro FOREIGN KEY (id_libro) REFERENCES libros(id),
CONSTRAINT FK_libro_autor_autor FOREIGN KEY (id_autor) REFERENCES autores(id_autor)
);



