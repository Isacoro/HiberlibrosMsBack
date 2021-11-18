package com.hiberlibros.HiberLibros.interfaces;

import com.hiberlibros.HiberLibros.entities.Editorial;
import java.util.List;
import java.util.Optional;


public interface IEditorialService {
    
    public Editorial encontrarPorId(Integer id);
    
    public List<Editorial> consultaTodas();

    public void altaModificacionEditorial(Editorial editorial);

    public Boolean bajaEditorial(Integer idEditorial);

    public Editorial consultaPorIdEditorial(int idEditorial);

    public List<Editorial> consultaPorNombreEditorial(Editorial editorial);
}
