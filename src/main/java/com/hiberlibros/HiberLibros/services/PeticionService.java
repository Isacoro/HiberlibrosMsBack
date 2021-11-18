package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Peticion;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.interfaces.ICorreoService;
import com.hiberlibros.HiberLibros.interfaces.IPeticionService;
import com.hiberlibros.HiberLibros.repositories.PeticionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;

@Service
public class PeticionService implements IPeticionService {

    @Autowired
    private PeticionRepository peticionRepository;
    @Autowired
    private IUsuarioLibroService usuarioLibroService;
    @Autowired
    private ICorreoService correoService;


    @Override
    public List<Peticion> consultaTodasPeticiones() {
        return peticionRepository.findAll();
    }
    
    @Override
    public Peticion consultarPeticionId(Integer id) {
        return peticionRepository.findById(id).get();
    }

    //guarda la petición y obtiene aquí los objetos UL y Usuario
    @Override
    public void insertaPeticion(Peticion p, Integer id_ul, Usuario u) {
        p.setIdUsuarioLibro(usuarioLibroService.encontrarId(id_ul));
        p.setIdUsuarioSolicitante(u);
        p.setAceptacion(false);
        p.setPendienteTratar(true);

        peticionRepository.save(p);
        
        String destinatario  = usuarioLibroService.encontrarId(id_ul).getUsuario().getMail();
        String asunto = "peticion de libro " + usuarioLibroService.encontrarId(id_ul).getLibro().getTitulo() ;
        String cuerpo = "le han solicitado el libro "+ usuarioLibroService.encontrarId(id_ul).getLibro().getTitulo()
                + "  autentiquese en Hiberlibros para gestionar la peticion: http://localhost:8091/hiberlibros";
        correoService.enviarCorreo(destinatario, asunto, cuerpo);
    }

    @Override
    public void insertaModificaPeticion(Peticion p) {
        peticionRepository.save(p);
    }

    @Override
    public void eliminaPeticion(Peticion p) {
        peticionRepository.deleteById(p.getId());
    }

    @Override
    public void eliminarId(Integer id) {
        peticionRepository.deleteById(id);;
    }

    @Override
    public void aceptarPeticion(Peticion p) {
        p.setAceptacion(Boolean.TRUE);
        p.setPendienteTratar(Boolean.FALSE);
        peticionRepository.save(p);
        
        
        String destinatario  = p.getIdUsuarioSolicitante().getMail();
        String asunto = "peticion de libro aceptada ";
        String cuerpo = " el usuario " + p.getIdUsuarioLibro().getUsuario().getNombre() 
                + " acepta el intercambio del libro " + p.getIdUsuarioLibro().getLibro().getTitulo()
                + " contacte con el en el telefono " + p.getIdUsuarioLibro().getUsuario().getTelef()
                +  " o mediante su correo electronico " + p.getIdUsuarioLibro().getUsuario().getMail();
        correoService.enviarCorreo(destinatario, asunto, cuerpo);
    }

    @Override
    public void rechazarPeticion(Integer id) {
        Peticion p = peticionRepository.findById(id).get();
        p.setAceptacion(Boolean.FALSE);
        p.setPendienteTratar(Boolean.FALSE);
        peticionRepository.save(p);
        
        String destinatario  = p.getIdUsuarioSolicitante().getMail();
        String asunto = "peticion de libro rechazada ";
        String cuerpo = " su peticion de intercambio del libro " + p.getIdUsuarioLibro().getLibro().getTitulo() + " ha sido rechazadas";
        correoService.enviarCorreo(destinatario, asunto, cuerpo);
    }

    @Override
    public List<Peticion> consultarPeticionesPendientes(Usuario u) {
        return peticionRepository.findByPendienteTratar(Boolean.TRUE).stream()
                .filter(x -> x.getIdUsuarioSolicitante()
                        .equals(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Peticion> consultarPeticionesAceptadas(Usuario u) {
        return peticionRepository.findByAceptacion(Boolean.TRUE).stream()
                .filter(x -> x.getIdUsuarioSolicitante()
                        .equals(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Peticion> consultarPeticionesRechazadas(Usuario u) {
        return peticionRepository.findByAceptacion(Boolean.FALSE).stream()
                .filter(x -> x.getIdUsuarioSolicitante()
                        .equals(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Peticion> consutarPeticionesUsuarioPendientes(Usuario u) {
        return peticionRepository.findByPendienteTratarAndIdUsuarioSolicitante(Boolean.TRUE, u);
    }

    @Override
    public List<Peticion> consultarPeticonesRecibidas(Usuario u) {
        List<Peticion> p = new ArrayList<>();
        List<UsuarioLibro> ul = usuarioLibroService.buscarUsuario(u);//busca la lista de libros de un usuario
        ul.forEach(x -> {
            List<Peticion> pAux = peticionRepository.findByIdUsuarioLibroAndPendienteTratar(x, Boolean.TRUE); //busca por UsuarioLibro y que este pendiente de tratar
            pAux.forEach(y -> {
                p.add(y);//lo va almacenando hasta tener todos. 
            });
        });
        return p;
    }

    @Override
    public void borrarPorUsuarioSolicitante(Usuario u) {
        peticionRepository.deleteByIdUsuarioSolicitante(u);
    }

    @Override
    public void borrarPorUsuarioLibro(UsuarioLibro ul) {
        peticionRepository.deleteByIdUsuarioLibro(ul);
    }

}
