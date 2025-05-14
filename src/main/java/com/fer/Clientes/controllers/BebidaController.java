package com.fer.Clientes.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fer.Clientes.model.Bebida;
import com.fer.Clientes.repository.BebidaRepository;

@RestController
@RequestMapping("/bebidas")
@CrossOrigin(origins = "*")
public class BebidaController {

    @Autowired
    private BebidaRepository bebidaRepository;

    @GetMapping("/traerBebidas")
    public List<Bebida> traerBebidas() {
        return bebidaRepository.findAll();
    }

    @GetMapping("/traer-Bebida/{id}")
    public ResponseEntity<Bebida> traerUnaBebida(@PathVariable Long id) {
        Optional<Bebida> bebida = bebidaRepository.findById(id);
        return bebida.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertarBebida")
    public Bebida insertarBebida(@RequestBody Bebida bebida) {
        return bebidaRepository.save(bebida);
    }

    @PutMapping("/editar-Bebida/{id}")
    public ResponseEntity<Bebida> actualizarBebida(@PathVariable Long id, @RequestBody Bebida bebida) {
        return bebidaRepository.findById(id).map(bebidaExistente -> {
            bebidaExistente.setNombre(bebida.getNombre());
            bebidaExistente.setTipo(bebida.getTipo());
            bebidaExistente.setPresentacion(bebida.getPresentacion());
            bebidaExistente.setPrecio(bebida.getPrecio());
            bebidaExistente.setDescripcion(bebida.getDescripcion());
            Bebida bebidaActualizada = bebidaRepository.save(bebidaExistente);
            return ResponseEntity.ok(bebidaActualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar-Bebida/{id}")
    public void eliminarBebida(@PathVariable Long id) {
        bebidaRepository.deleteById(id);
    }
}
