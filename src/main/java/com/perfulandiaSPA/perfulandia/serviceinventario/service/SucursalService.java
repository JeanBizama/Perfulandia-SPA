package com.perfulandiaSPA.perfulandia.serviceinventario.service;

import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import com.perfulandiaSPA.perfulandia.serviceinventario.repository.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Sucursal findById(Long id) {
        return sucursalRepository.findById(id).get();
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void delete(Long id) {
        sucursalRepository.deleteById(id);
    }
}


