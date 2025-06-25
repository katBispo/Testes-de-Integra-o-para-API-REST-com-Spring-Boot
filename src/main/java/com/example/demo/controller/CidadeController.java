package com.example.demo.controller;

import com.example.demo.model.Cidade;
import com.example.demo.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    // POST - Salvar nova cidade
    @PostMapping
    public ResponseEntity<Cidade> salvarCidade(@RequestBody Cidade cidade) {
        Cidade novaCidade = cidadeService.salvar(cidade);
        return ResponseEntity.ok(novaCidade);
    }

    // GET - Buscar cidade por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Cidade> buscarPorNome(@PathVariable String nome) {
        return cidadeService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar todas as cidades
    @GetMapping
    public ResponseEntity<List<Cidade>> listarTodas() {
        return ResponseEntity.ok(cidadeService.listarTodas());
    }
}
