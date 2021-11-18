package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Editorial;
import com.hiberlibros.HiberLibros.interfaces.IEditorialService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/editorialesback")
public class EditorialController {

    @Autowired
    private IEditorialService serviceEditorial;

    @RequestMapping(value="/editoriales", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, Object> editoriales(Editorial editorial) {
        Map<String,Object> m = new HashMap<>();
        if (editorial.getId() == null) {
            editorial = new Editorial();
        } else {
            editorial = serviceEditorial.consultaPorIdEditorial(editorial.getId());
        }
        m.put("editorial", editorial);
        m.put("editoriales", serviceEditorial.consultaTodas());
        return m;
    }

    @PostMapping("/alta")
    public Map<String,Object> editorialesAlta(Editorial ed) {
        List<Editorial> editoriales = serviceEditorial.consultaPorNombreEditorial(ed);
        Map<String,Object> m = new HashMap<>();
        
        String errMensaje = null;
        if (editoriales.size() > 0) {
            errMensaje = "editorial existente";
        } else {
            serviceEditorial.altaModificacionEditorial(ed);
            m.put("editorial", serviceEditorial.consultaPorIdEditorial(ed.getId()));
        }
        
        m.put("errMensaje", errMensaje);
        
        return m;
    }

    @GetMapping("/eliminarEditorial")
    public String editorialesBaja(Integer id) {
        String borrado="";
        if (serviceEditorial.bajaEditorial(id)) {
            borrado="Editorial borrada";
        } else {
            borrado="Error, no es posible borrar esta editorial";
        }
        return borrado;
    }

    @PostMapping("/modificacion")
    public void editorialesModificacion(Editorial ed) {
        serviceEditorial.altaModificacionEditorial(ed);
    }

    @PostMapping("consulta")
    public Editorial editorialesConsulta(String id) {
        return serviceEditorial.consultaPorIdEditorial(Integer.parseInt(id));
    }
    
    
    @GetMapping("/listarAdmin")
    public Map<String,Object> listaAdmin(String borrado) {
        Map<String, Object> m = new HashMap<>();
        m.put("editoriales", serviceEditorial.consultaTodas());
        if(borrado!=null){
            m.put("borrado", borrado);
        }
        return m;
    }
    
    
    //  funcionalidad repetida, se comenta
    // 
//    @GetMapping("/editar")
//    @ResponseBody
//    public Editorial editarEdit(Integer id) {
//        Editorial edit = serviceEditorial.consultaPorIdEditorial(id);
//        return edit;
//    }
}