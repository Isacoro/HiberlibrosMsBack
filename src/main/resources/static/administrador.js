$(document).ready(function () {
    bootbox.alert(
            $("#mensajeBorrado").html());
});
function modificarLibros(pId) {
    $.ajax({
        url: '/libros/modificar',
        data: {
            id: pId
        },
        success: function (pHtml) {
            bootbox.dialog({
                title: "Modificar libro",
                size: "large",
                message: pHtml
            })
        },
        error: function (err) {
            alert('Error 404, page not found')
        }
    });
}

function modificarRelato(pId) {
    $.ajax({
        url: '/relato/modificar',
        data: {
            id: pId
        },
        success: function (pHtml) {
            bootbox.dialog({
                title: "Modificar relato",
                size: "large",
                message: pHtml
            })
        },
        error: function (err) {
            alert('Error 404, page not found')
        }
    });
}


function mostrarLibros(pId) {
    $.ajax({
        url: '/librosAutor',
        data: {
            id: pId
        },
        success: function (pHtml) {
            bootbox.dialog({
                animate: true,
                size: "large",
                message: pHtml
            })
        },
        error: function (err) {
            alert('Error 404, page not found')
        }
    });
}

function editarAutor(pId) {
    $.ajax({
        url: '/editarAutor',
        data: {
            id: pId
        },
        success: function (pHtml) {
            bootbox.dialog({

                size: "large",
                message: pHtml
            })
        },
        error: function (err) {
            alert('Error 404, page not found')
        }
    });
}

function altaUsuario() {
    bootbox.dialog({
        Title: "Alta usuario",
        size: "large",
        message: $('#altaUsuario').html()
    });


}


function eliminarLibro(pID) {

    bootbox.confirm({
        size: "small",
        message: "¿Estás seguro?",
        callback: function (result) {
            if (result) {
                window.location.href = "/libros/eliminarAdmin?id=" + pID;
            }
        }
    });
}

function eliminarUsuario(pID) {

    bootbox.confirm({
        size: "small",
        message: "¿Estás seguro?",
        callback: function (result) {
            if (result) {
                window.location.href = "/usuarios/borrar?id=" + pID;
            }
        }
    });
}

function eliminarRelato(pID) {

    bootbox.confirm({
        size: "small",
        message: "¿Estás seguro?",
        callback: function (result) {
            if (result) {
                window.location.href = "/relato/eliminarAdmin?id=" + pID;
            }
        }
    });
}

function eliminarAutor(pID) {

    bootbox.confirm({
        size: "small",
        message: "¿Estás seguro?",
        callback: function (result) {
            if (result) {
                window.location.href = "/eliminarAutor?id=" + pID;
            }
        }
    });
}

function editarGenero(pID) {
    $.ajax({
        url: '/genero/editar',
        data: {id: pID},
        datatype: 'json',
        success: function (pJson) {
            bootbox.dialog({
                title: 'Editar',
                size: 'large',
                message: "<div id='editar'>" + $("#editarGenero").html() + "</div>"
            });
            $("#editar form").deserialize(pJson);
        }

    });


}

function editarEditorial(pID) {
    $.ajax({
        url: '/editoriales/editar',
        data: {id: pID},
        datatype: 'json',
        success: function (pJson) {
            bootbox.dialog({
                title: 'Modificar editorial',
                size: 'large',
                message: "<div id='editar'>" + $("#editarEditorial").html() + "</div>"
            });
            $("#editar form").deserialize(pJson);
        }

    });


}

function mostrarContactos() {
    $.ajax({
        url: '/hiberlibros/paneladmin/contacto',
        success: function (pHtml) {
            bootbox.dialog({
                size: "small",
                message: pHtml
            })
        },
        error: function (err) {
            alert('Error 404, page not found')
        }
    });
}
function previsualizar() {
    var text = document.getElementById("urlPortada").value;
    $("#previsualizacion").attr("src", text);
    $("#previsualizacion").hide();
    $("#previsualizacion").fadeIn(1000);
}

///////////////////////////////////////////////////////////////////

$(function () {
    $("#container").simpleCalendar();
});
$("#container").simpleCalendar({

// displays events
    displayEvent: true,
    // event dates
    events: calendarData,

    //Dishabilitar descripcion de evento
    disableEventDetails: false,
    disableEmptyDetails: false

});
$("#container").simpleCalendar({

    displayYear: true

});
$("#container").simpleCalendar({

    months: ['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre'],
    days: ['domingo', 'lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado'],
});
$("#container").simpleCalendar({

    fixedStartDay: false

});
$("#container").simpleCalendar({

// called after init
    onInit: function (calendar) {},
    // called on month change
    onMonthChange: function (month, year) {},
    // called on date selection
    onDateSelect: function (date, events) {}

});



