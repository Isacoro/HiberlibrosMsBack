package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import java.util.List;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.IPreferenciaService;
import com.hiberlibros.HiberLibros.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Isabel
 */
@Service
public class GeneroService implements IGeneroService {
    
    @Autowired
    private GeneroRepository repoGenero;
    
    @Autowired
    private ILibroService serviceLS;
    
    @Autowired
    private IPreferenciaService servicePrefe;
    
    @Override
    public Genero encontrarPorId(Integer id) {
        return repoGenero.findById(id).get();
    }

    @Override
    public void guardarGenero(Genero genero) {
        genero.setDesactivado(Boolean.FALSE);
        repoGenero.save(genero);
    }

    @Override
    public Boolean borrarGenero(Integer id) {
        Genero g=encontrarPorId(id);
        List<Libro> l=serviceLS.encontrarPorGenero(g);
        List<Preferencia> p=servicePrefe.encontrarPorGenero(g);
        if((l.isEmpty() || l==null)&&(p.isEmpty() || p==null)){
            repoGenero.deleteById(id);
            return true;
        }else if (p.isEmpty() || p==null){
            Boolean result=serviceLS.bajaLibrosList(l);
            if(result){
                g.setDesactivado(Boolean.TRUE);
                repoGenero.save(g);
            }
            return result;        
        }else{
            return false;
        }        
    }

    @Override
    public List<Genero> getGeneros() {
        return repoGenero.findByDesactivado(Boolean.FALSE);
    }

    
}
