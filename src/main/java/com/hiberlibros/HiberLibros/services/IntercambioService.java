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

/**
 *
 * @author Usuario
 */
@Service
public class IntercambioService implements IIntercambioService {

    @Autowired
    private IntercambioRepository repoInter;

    @Autowired
    private IUsuarioLibroService serviceUL;

    @Override
    public void guardarIntercambio(UsuarioLibro ulPrestatario, UsuarioLibro ulPrestador) {
        Intercambio i = new Intercambio();
        Date date = Date.from(Instant.now());
        i.setFechaPrestamo(date);
        i.setUsuarioPrestador(ulPrestador);
        i.setUsuarioPrestatario(ulPrestatario);
        ulPrestatario.setEstadoPrestamo("ocupado");
        serviceUL.editar(ulPrestatario);
        ulPrestador.setEstadoPrestamo("ocupado");
        serviceUL.editar(ulPrestador);
        repoInter.save(i);
    }

    @Override
    public List<Intercambio> encontrarULPrestador(List<UsuarioLibro> ul) {
        List<Intercambio> iList = new ArrayList<>();
        ul.forEach(x -> {
            List<Intercambio> i = repoInter.findByUsuarioPrestador(x);
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
            List<Intercambio> i = repoInter.findByUsuarioPrestatario(x);
            if (i.size() != 0) {
                i.forEach(y -> iList.add(y));
            }
        });
        return iList;
    }

    @Override
    public void finIntercambio(Integer id) {
        Intercambio i = repoInter.findById(id).get();
        Date date = Date.from(Instant.now());
        i.setFechaDevolucion(date);
        repoInter.save(i);
        UsuarioLibro ulPrestador = i.getUsuarioPrestador();
        ulPrestador.setEstadoPrestamo("Libre");
        serviceUL.editar(ulPrestador);
        UsuarioLibro ulPrestatario = i.getUsuarioPrestatario();
        ulPrestatario.setEstadoPrestamo("Libre");
        serviceUL.editar(ulPrestatario);

    }

    @Override
    public Boolean intercambioPendienteFinalizar(UsuarioLibro ul) {//metodo para comprobar si queda algún intercambio por finalizar
        List<Intercambio> i = new ArrayList<>();
        i = repoInter.findByUsuarioPrestadorAndFechaDevolucion(ul, null);
        if (i.size() != 0 || i != null) {
            return false;
        } else {
            i = repoInter.findByUsuarioPrestatarioAndFechaDevolucion(ul, null);
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
        result+=ul.stream().map(x->repoInter.countByFechaDevolucionAndUsuarioPrestador(null, x)).collect(Collectors.summingInt(Integer::intValue));
        result+=ul.stream().map(x->repoInter.countByFechaDevolucionAndUsuarioPrestatario(null, x)).collect(Collectors.summingInt(Integer::intValue));
        return result;
    }

}
