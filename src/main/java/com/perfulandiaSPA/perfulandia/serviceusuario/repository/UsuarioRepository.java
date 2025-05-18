package com.perfulandiaSPA.perfulandia.serviceusuario.repository;

import com.perfulandiaSPA.perfulandia.serviceusuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query(value = "SELECT * FROM usuario u WHERE u.correo = :correo", nativeQuery = true)
    Usuario findByCorreo(String correo);
    
}
