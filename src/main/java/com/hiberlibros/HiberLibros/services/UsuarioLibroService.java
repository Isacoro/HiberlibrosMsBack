package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.repositories.UsuarioLibroRepository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;


@Service
public class UsuarioLibroService implements IUsuarioLibroService {

    @Autowired
    private UsuarioLibroRepository usuarioLibroRepository;
    @Autowired
    private LibroService libroService;

    @Override
    public UsuarioLibro encontrarId(Integer id) {
        return usuarioLibroRepository.findById(id).get();
    }

    @Override
    public List<UsuarioLibro> buscarContiene(String buscador, Integer id) {
        List<UsuarioLibro> ul = new ArrayList<>();
        List<Libro> l = libroService.buscarLibro(buscador); //busca libros que contentan ese parámetro
        l.forEach(x -> {
            List<UsuarioLibro> ulAux = usuarioLibroRepository.findByLibroAndQuieroTengoAndEstadoPrestamo(x, "Tengo", "Libre");//Encuentra los libros que coiniciden dentro de usuarioLibros
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
        return usuarioLibroRepository.findByUsuario(u);
    }

    @Override
    public List<UsuarioLibro> todos() {
        return usuarioLibroRepository.findAll();
    }

    @Override
    public void guardar(UsuarioLibro ul, Libro l, Usuario u) {//guarda el registro
        ul.setLibro(l);
        ul.setUsuario(u);
        ul.setDesactivado(Boolean.FALSE);
        usuarioLibroRepository.save(ul);

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
        return usuarioLibroRepository.findByUsuarioAndQuieroTengoAndEstadoPrestamo(u, tengo, disponibilidad);
    }

    @Override
    public void editar(UsuarioLibro ul) {
        usuarioLibroRepository.save(ul);
    }

    @Override
    public List<UsuarioLibro> buscarUsuariotiene(Usuario u) {
        return usuarioLibroRepository.findByUsuarioAndDesactivadoOrderByQuieroTengoAsc(u, Boolean.FALSE);
    }

    @Override
    public List<UsuarioLibro> buscarDisponibles(Usuario u) {
        return usuarioLibroRepository.findByUsuarioNotAndQuieroTengoAndEstadoPrestamoAndDesactivado(u, "Tengo", "Libre",Boolean.FALSE);
    }

    @Override
    public Boolean usuarioBorrado(Usuario u) {
        List<UsuarioLibro> ul = usuarioLibroRepository.findByUsuarioAndDesactivadoAndEstadoPrestamo(u, Boolean.FALSE, "ocupado");
        if (!ul.isEmpty()) {
            return false;
        } else {
            ul = usuarioLibroRepository.findByUsuarioAndDesactivado(u, Boolean.FALSE);
            ul.forEach(x -> {
                x.setDesactivado(Boolean.TRUE);
                usuarioLibroRepository.save(x);
            });
            return true;
        }
    }

    @Override
    public Boolean libroBorrado(Libro l) {
        List<UsuarioLibro> ul = usuarioLibroRepository.findByLibroAndDesactivadoAndEstadoPrestamo(l, Boolean.FALSE, "ocupado");
        if (!ul.isEmpty()) {
            return false;
        } else {
            ul = usuarioLibroRepository.findByLibroAndDesactivado(l, Boolean.FALSE);
            ul.forEach(x -> {
                x.setDesactivado(Boolean.TRUE);
                usuarioLibroRepository.save(x);
            });
            return true;
        }
    }

    @Override
    public List<UsuarioLibro> buscarLibro(Libro l) {
        return usuarioLibroRepository.findByLibroAndDesactivadoAndEstadoPrestamo(l, Boolean.FALSE, "ocupado");
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
        return usuarioLibroRepository.countByUsuarioAndDesactivado(u, Boolean.FALSE);
    }

}
