package com.example.demo.repository;

import com.example.demo.model.Frete;
import com.example.demo.model.Cidade;
import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {

    List<Frete> findByClienteOrderByValorAsc(Cliente cliente);

    Frete findTopByOrderByValorDesc();

    @Query("SELECT f.cidade FROM Frete f GROUP BY f.cidade ORDER BY COUNT(f) DESC LIMIT 1")
    Cidade cidadeComMaisFretes();

    List<Frete> findByClienteCodigoClienteOrderByValorAsc(Integer codigoCliente);

}   