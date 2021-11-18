package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.repositories.RelatoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IRelatoService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.UUID;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RelatoService implements IRelatoService {


    @Autowired
    private RelatoRepository relatoRepository;
    
    @Autowired IUsuarioService serviceUsu;

    @Override
    public List<Relato> buscarRelato(String titulo) {
        return relatoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public List<Relato> todos() {
        return relatoRepository.findAll();
    }
    
    @Override
    public List<Relato> buscarPorValoracionMayorAMenor() {
        List<Relato> rel = relatoRepository.findAll();
        List<Relato> relato = rel.stream().sorted(Comparator.comparingDouble(Relato::getValoracionUsuarios)).collect(Collectors.toList());

        return relato;
    }

    @Override
    public List<Relato> buscarPorValoracionMenorAMayor() {
        List<Relato> rel = relatoRepository.findAll();
        List<Relato> relato = rel.stream().sorted(Comparator.comparingDouble(Relato::getValoracionUsuarios).reversed()).collect(Collectors.toList());

        return relato;
    }

    @Override
    public List<Relato> encontrarPorAutor(Usuario u) {
        return relatoRepository.findByUsuario(u);
    }

    @Override
    public void guardarRelato(String RUTA_BASE, Relato relato, MultipartFile ficherosubido, Integer id) {
       String nombre = UUID.randomUUID().toString();
        String nombreFichero = ficherosubido.getOriginalFilename().toLowerCase();
        String extension = nombreFichero.substring(nombreFichero.lastIndexOf("."));
        System.out.println("Extension : " + extension);
        String subir = RUTA_BASE + nombre + extension;
        File f = new File(subir);
        f.getParentFile().mkdirs();
        try {
            Files.copy(ficherosubido.getInputStream(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
            relato.setFichero(subir);
            relato.setValoracionUsuarios(new Double(0));
            relato.setNumeroValoraciones(0);
            relatoRepository.save(relato);
        } catch (Exception e) {
            e.printStackTrace(); 

        }
    }
}
