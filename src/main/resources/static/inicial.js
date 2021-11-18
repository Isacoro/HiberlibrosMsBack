function registrarUsuario() {
    bootbox.dialog({
        title: 'Registro Usuario',
        size: 'large',
        message: $("#nuevoUsuario").html()
    });

}
function editarUsuario() {
    $.ajax({
        url: '/hiberlibros/editarUsuario',
        datatype: 'json',
        success: function (pJson) {
            bootbox.dialog({
                title: 'Editar',
                size: 'large',
                message: "<div id='editar'>" + $("#editarUsuario").html() + "</div>"
            });
            $("#editar form").deserialize(pJson);
        }

    });


}
function eliminarU(pID) {

    bootbox.confirm({
        size: "small",
        message: "¿Estás seguro?",
        callback: function (result) {
            if (result) {
                window.location.href = "/usuarios/borrarUsuario?id=" + pID;
            }
        }
    });
}
function consultarLibros(pID) {
    $.ajax({
        url: '/getLibrosAutor',
        data: {
            id: pID
        },
        datatype: 'json',
        success: function (json) {
            $("#listaLibros").html("<table class='table table-striped col-12' id='tabla'>");
            $("#tabla").append("<thead><tr><th>Titulo</th><th>Valoracion</th></tr></thead>");
            $.each(json, function (key, value) {
                var fila = $("<tr>");
                $("<td>").html(value.titulo + "</td>").appendTo(fila);
                $("<td>").html(value.valoracionLibro + "</td>").appendTo(fila);
                fila.append("</tr>");
                fila.appendTo("#tabla");
            });
            bootbox.dialog({
                title: 'Lista de libros',
                size: 'medium',
                message: $("#listaLibros").html()
            });
        }
    });
}
function anyadirAutor() {
    bootbox.dialog({
        title: 'Añadir autor',
        size: 'large',
        message: "<div id='autorForm'>" + $("#autor").html() + "</div>"
    });

}

function gestionarPeticion(pId) {
    $.ajax({
        url: "gestionarPeticion",
        data: {
            id: pId
        },
        success: function (pHtml) {
            bootbox.dialog({
                title: "Guardar intercambio",
                size: "large",
                message: pHtml
            })
        },
        error: function (err) {
            alert('Error 404, page not found')
        }
    });

}

function valoracion(value, row) {
    return "<span>" + value + "</span>" +
            "<i class='fas fa-star' style='color:orange'></i>";
}

function btnReservar(value, row) {
    return "<a href='/peticion/alta?id_ul=" + value + "' class='btn btn-secondary'>Pedir</a>";
}

function btnForo(value, row) {
    return "<a href='/foros/libro?id=" + value + "' class='btn btn-secondary'>Foro</a>";
}

function btnValoracion(value, row) {
    return "<form action='/libros/addValoracionLibro' method='post'>" +
            "<input type='hidden' name='id'  value='" + value + "' />" +
            "<span class='clasificacion'>" +
            "<input class='puntuacion' id='" + value + "radio5' type='radio' name='valoracion' value='5'>" +
            "<label for='" + value + "radio5' class='estrella'>★</label>" +
            "<input class='puntuacion' id='" + value + "radio4' type='radio' name='valoracion' value='4'>" +
            "<label for='" + value + "radio4' class='estrella'>★</label>" +
            "<input class='puntuacion' id='" + value + "radio3' type='radio' name='valoracion' value='3'>" +
            "<label for='" + value + "radio3'  class='estrella'>★</label>" +
            "<input class='puntuacion' id='" + value + "radio2' type='radio' name='valoracion' value='2'>" +
            "<label for='" + value + "radio2' class='estrella'>★</label>" +
            "<input class='puntuacion'  id='" + value + "radio1' type='radio' name='valoracion' value='1'>" +
            "<label for='" + value + "radio1'  class='estrella'>★</label>" +
            "</span>" +
            '<div class="row"><div class="col-3"> <button type="submit" class="btn btn-sm btn-warning">Valorar</button></div>' +
            "</form>" +
            "<div class='col-6'> <a href='/foros/libro' class='btn btn-sm btn-secondary'>Ir a foro</a></div></div>";

}

function formForo() {
    bootbox.dialog({
        title: 'Añadir Foro',
        size: 'large',
        message: "<div id='foroForm'>" + $("#formularioForo").html() + "</div>"
    }
    );

}

function image(value,row) {
	return "<img src='"+value+"' style='width:100px; height:120px;'>";
}

function previsualizar() {
    var text =  document.getElementById("urlportada").value;
	$("#previsualizacion").attr("src", text);
	$("#mostrarImagen").hide();
	$("#mostrarImagen").fadeIn(1000);
}


