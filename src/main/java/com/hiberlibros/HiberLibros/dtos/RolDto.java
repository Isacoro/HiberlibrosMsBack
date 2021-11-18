package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDto implements GrantedAuthority {

    private String nombre;

    @Override
    public String getAuthority() {
        return nombre;
    }
}
