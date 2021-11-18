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


@Service
public class GeneroService implements IGeneroService {
    
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private ILibroService libroService;
    @Autowired
    private IPreferenciaService preferenciaService;


    @Override
    public Genero encontrarPorId(Integer id) {
        return generoRepository.findById(id).get();
    }

    @Override
    public void guardarGenero(Genero genero) {
        genero.setDesactivado(Boolean.FALSE);
        generoRepository.save(genero);
    }

    @Override
    public Boolean borrarGenero(Integer id) {
        Genero g=encontrarPorId(id);
        List<Libro> l= libroService.encontrarPorGenero(g);
        List<Preferencia> p= preferenciaService.encontrarPorGenero(g);
        if((l.isEmpty() || l==null)&&(p.isEmpty() || p==null)){
            generoRepository.deleteById(id);
            return true;
        }else if (p.isEmpty() || p==null){
            Boolean result= libroService.bajaLibrosList(l);
            if(result){
                g.setDesactivado(Boolean.TRUE);
                generoRepository.save(g);
            }
            return result;        
        }else{
            return false;
        }        
    }

    @Override
    public List<Genero> getGeneros() {
        return generoRepository.findByDesactivado(Boolean.FALSE);
    }
}
