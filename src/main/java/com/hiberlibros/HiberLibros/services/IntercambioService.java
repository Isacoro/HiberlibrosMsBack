package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Intercambio;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.interfaces.IIntercambioService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;
import com.hiberlibros.HiberLibros.repositories.IntercambioRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IntercambioService implements IIntercambioService {

    @Autowired
    private IntercambioRepository intercambioRepository;
    @Autowired
    private IUsuarioLibroService usuarioLibroService;

    @Override
    public void guardarIntercambio(UsuarioLibro ulPrestatario, UsuarioLibro ulPrestador) {
        Intercambio i = new Intercambio();
        Date date = Date.from(Instant.now());
        i.setFechaPrestamo(date);
        i.setUsuarioPrestador(ulPrestador);
        i.setUsuarioPrestatario(ulPrestatario);
        ulPrestatario.setEstadoPrestamo("ocupado");
        usuarioLibroService.editar(ulPrestatario);
        ulPrestador.setEstadoPrestamo("ocupado");
        usuarioLibroService.editar(ulPrestador);
        intercambioRepository.save(i);
    }

    @Override
    public List<Intercambio> encontrarULPrestador(List<UsuarioLibro> ul) {
        List<Intercambio> iList = new ArrayList<>();
        ul.forEach(x -> {
            List<Intercambio> i = intercambioRepository.findByUsuarioPrestador(x);
            if (i.size() != 0) {
                i.forEach(y -> iList.add(y));
            }
        });
        return iList;
    }

    @Override
    public List<Intercambio> encontrarULPrestatario(List<UsuarioLibro> ul) {
        List<Intercambio> iList = new ArrayList<>();
        ul.forEach(x -> {
            List<Intercambio> i = intercambioRepository.findByUsuarioPrestatario(x);
            if (i.size() != 0) {
                i.forEach(y -> iList.add(y));
            }
        });
        return iList;
    }

    @Override
    public void finIntercambio(Integer id) {
        Intercambio i = intercambioRepository.findById(id).get();
        Date date = Date.from(Instant.now());
        i.setFechaDevolucion(date);
        intercambioRepository.save(i);
        UsuarioLibro ulPrestador = i.getUsuarioPrestador();
        ulPrestador.setEstadoPrestamo("Libre");
        usuarioLibroService.editar(ulPrestador);
        UsuarioLibro ulPrestatario = i.getUsuarioPrestatario();
        ulPrestatario.setEstadoPrestamo("Libre");
        usuarioLibroService.editar(ulPrestatario);
    }

    @Override
    public Boolean intercambioPendienteFinalizar(UsuarioLibro ul) {//metodo para comprobar si queda algún intercambio por finalizar
        List<Intercambio> i = new ArrayList<>();
        i = intercambioRepository.findByUsuarioPrestadorAndFechaDevolucion(ul, null);
        if (i.size() != 0 || i != null) {
            return false;
        } else {
            i = intercambioRepository.findByUsuarioPrestatarioAndFechaDevolucion(ul, null);
            if (i.size() != 0 || i != null) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public Integer contarIntercambiosPendientes(List<UsuarioLibro> ul) {
        Integer result=0;
        result+=ul.stream()
                .map(x-> intercambioRepository.countByFechaDevolucionAndUsuarioPrestador(null, x))
                .collect(Collectors.summingInt(Integer::intValue));

        result+=ul.stream()
                .map(x-> intercambioRepository.countByFechaDevolucionAndUsuarioPrestatario(null, x))
                .collect(Collectors.summingInt(Integer::intValue));

        return result;
    }
}
