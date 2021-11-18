package com.hiberlibros.HiberLibros.dtos;

import com.hiberlibros.HiberLibros.entities.Peticion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeticionDto {

    private Peticion peticion;
    private List<Peticion> peticiones;
}
