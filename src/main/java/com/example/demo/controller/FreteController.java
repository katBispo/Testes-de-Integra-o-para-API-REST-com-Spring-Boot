package com.example.demo.controller;

import com.example.demo.model.Frete;
import com.example.demo.model.Cidade;
import com.example.demo.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteService freteService;

    // POST - Cadastrar um novo frete
    @PostMapping
    public ResponseEntity<Frete> cadastrarFrete(@RequestBody Frete frete) {
        try {
            Frete novoFrete = freteService.cadastrarFrete(frete);
            return ResponseEntity.ok(novoFrete);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // GET - Listar todos os fretes
    @GetMapping
    public ResponseEntity<List<Frete>> listarTodos() {
        return ResponseEntity.ok(freteService.listarTodos());
    }

    // GET - Listar fretes de um cliente ordenados por valor
    @GetMapping("/cliente/{codigoCliente}")
    public ResponseEntity<List<Frete>> listarPorCliente(@PathVariable Integer codigoCliente) {
        return ResponseEntity.ok(freteService.listarPorClienteOrdenadoPorValor(codigoCliente));
    }

    // GET - Frete com o maior valor
    @GetMapping("/maior")
    public ResponseEntity<Frete> freteComMaiorValor() {
        return ResponseEntity.ok(freteService.freteComMaiorValor());
    }

    // GET - Cidade com mais fretes
    @GetMapping("/cidade-mais-fretes")
    public ResponseEntity<Cidade> cidadeComMaisFretes() {
        return ResponseEntity.ok(freteService.cidadeComMaisFretes());
    }
}
