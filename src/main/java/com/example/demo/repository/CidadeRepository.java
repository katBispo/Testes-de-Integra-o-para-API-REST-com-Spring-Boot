package com.example.demo.repository;


import com.example.demo.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    Optional<Cidade> findByNome(String nome);
}