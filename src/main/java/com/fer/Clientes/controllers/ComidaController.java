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

import com.fer.Clientes.model.Comida;
import com.fer.Clientes.repository.ComidaRepository;

@RestController
@RequestMapping("/comidas")
@CrossOrigin(origins = "*")
public class ComidaController {

    @Autowired
    private ComidaRepository comidaRepository;

    @GetMapping("/traerComidas")
    public List<Comida> traerComidas() {
        return comidaRepository.findAll();
    }

    @GetMapping("/traer-Comida/{id}")
    public ResponseEntity<Comida> traerUnaComida(@PathVariable Long id) {
        Optional<Comida> comida = comidaRepository.findById(id);
        return comida.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertarComida")
    public Comida insertarComida(@RequestBody Comida comida) {
        return comidaRepository.save(comida);
    }

    @PutMapping("/editar-Comida/{id}")
    public ResponseEntity<Comida> actualizarComida(@PathVariable Long id, @RequestBody Comida comida) {
        return comidaRepository.findById(id).map(comidaExistente -> {
            comidaExistente.setNombre(comida.getNombre());
            comidaExistente.setPrecio(comida.getPrecio());
             comidaExistente.setCategoria(comida.getCategoria());
            comidaExistente.setDescripcion(comida.getDescripcion());
            Comida comidaActualizada = comidaRepository.save(comidaExistente);
            return ResponseEntity.ok(comidaActualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar-Comida/{id}")
    public void eliminarComida(@PathVariable Long id) {
        comidaRepository.deleteById(id);
    }
}
