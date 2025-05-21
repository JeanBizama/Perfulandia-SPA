package com.perfulandiaSPA.perfulandia.servicepedido.service;

import com.perfulandiaSPA.perfulandia.servicepedido.model.ProductoPedido;
import com.perfulandiaSPA.perfulandia.servicepedido.repository.ProductoPedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class ProductoPedidoService {

    @Autowired
    private ProductoPedidoRepository productoPedidoRepository;

    public List<ProductoPedido> findAll() {
        return productoPedidoRepository.findAll();
    }

    public ProductoPedido findById(Long id) {
        return productoPedidoRepository.findById(id).get();
    }

    public ProductoPedido save(ProductoPedido productoPedido) {
        return productoPedidoRepository.save(productoPedido);
    }

    public void delete(Long id) {
        productoPedidoRepository.deleteById(id);
    }


}
