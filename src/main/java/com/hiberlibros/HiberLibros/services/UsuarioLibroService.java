package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.repositories.UsuarioLibroRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;

/**
 *
 * @author Usuario
 */
@Service
public class UsuarioLibroService implements IUsuarioLibroService {

    @Autowired
    private UsuarioLibroRepository ulRepo;
    @Autowired
    private LibroService libService;

    @Override
    public UsuarioLibro encontrarId(Integer id) {
        return ulRepo.findById(id).get();
    }

    @Override
    public List<UsuarioLibro> buscarContiene(String buscador, Integer id) {
        List<UsuarioLibro> ul = new ArrayList<>();
        List<Libro> l = libService.buscarLibro(buscador); //busca libros que contentan ese parámetro       
        l.forEach(x -> {
            List<UsuarioLibro> ulAux = ulRepo.findByLibroAndQuieroTengoAndEstadoPrestamo(x, "Tengo", "Libre");//Encuentra los libros que coiniciden dentro de usuarioLibros
            ulAux.forEach(y -> {
                if (y.getUsuario().getId() != id) {
                    ul.add(y);
                }
            });
        });

        return ul;
    }

    @Override
    public List<UsuarioLibro> buscarUsuario(Usuario u) {//busca por usuario
        return ulRepo.findByUsuario(u);
    }

    @Override
    public List<UsuarioLibro> todos() {
        return ulRepo.findAll();
    }

    @Override
    public void guardar(UsuarioLibro ul, Libro l, Usuario u) {//guarda el registro
        ul.setLibro(l);
        ul.setUsuario(u);
        ul.setDesactivado(Boolean.FALSE);
        ulRepo.save(ul);

    }

    @Override
    public Boolean borrar(Integer id) {
        UsuarioLibro ul = encontrarId(id);
        if(ul.getEstadoPrestamo().equals("ocupado")){
            return false;
        }else{
            ul.setDesactivado(Boolean.TRUE);
            editar(ul);
            return true;
        }
    }

    @Override
    public List<UsuarioLibro> buscarUsuarioDisponibilidad(Usuario u, String tengo, String disponibilidad) {
        return ulRepo.findByUsuarioAndQuieroTengoAndEstadoPrestamo(u, tengo, disponibilidad);
    }

    @Override
    public void editar(UsuarioLibro ul) {
        ulRepo.save(ul);
    }

    @Override
    public List<UsuarioLibro> buscarUsuariotiene(Usuario u) {
        return ulRepo.findByUsuarioAndDesactivadoOrderByQuieroTengoAsc(u, Boolean.FALSE);
    }

    @Override
    public List<UsuarioLibro> buscarDisponibles(Usuario u) {
        return ulRepo.findByUsuarioNotAndQuieroTengoAndEstadoPrestamoAndDesactivado(u, "Tengo", "Libre",Boolean.FALSE);
    }

    @Override
    public Boolean usuarioBorrado(Usuario u) {
        List<UsuarioLibro> ul = ulRepo.findByUsuarioAndDesactivadoAndEstadoPrestamo(u, Boolean.FALSE, "ocupado");
        if (!ul.isEmpty()) {
            return false;
        } else {
            ul = ulRepo.findByUsuarioAndDesactivado(u, Boolean.FALSE);
            ul.forEach(x -> {
                x.setDesactivado(Boolean.TRUE);
                ulRepo.save(x);
            });
            return true;
        }
    }

    @Override
    public Boolean libroBorrado(Libro l) {
        List<UsuarioLibro> ul = ulRepo.findByLibroAndDesactivadoAndEstadoPrestamo(l, Boolean.FALSE, "ocupado");
        if (!ul.isEmpty()) {
            return false;
        } else {
            ul = ulRepo.findByLibroAndDesactivado(l, Boolean.FALSE);
            ul.forEach(x -> {
                x.setDesactivado(Boolean.TRUE);
                ulRepo.save(x);
            });
            return true;
        }
    }

    @Override
    public List<UsuarioLibro> buscarLibro(Libro l) {
        return ulRepo.findByLibroAndDesactivadoAndEstadoPrestamo(l, Boolean.FALSE, "ocupado");
    }

    @Override
    public Boolean librosOcupado(List<Libro> l) {
        List<UsuarioLibro> ul = new ArrayList<>();
        l.forEach(x -> {
            List<UsuarioLibro> ulAux = buscarLibro(x);
            ulAux.forEach(y -> {
                ul.add(y);
            });
        });
        return (!ul.isEmpty()); //si no hay ningún libro ocupado devuelve verdadero por lo que podría hacer la desactivación de autor o el de libro
        //si hay algún libro ocupado no se puede borrar porque están en medio de un intercambio
    }

    @Override
    public Integer contarLibrosPorUsuario(Usuario u) {
        return ulRepo.countByUsuarioAndDesactivado(u, Boolean.FALSE);
    }

}
