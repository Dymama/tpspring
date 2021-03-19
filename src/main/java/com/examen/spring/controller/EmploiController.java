package com.examen.spring.controller;

import com.examen.spring.exception.ResourceNotFoundException;
import com.examen.spring.model.Emploi;
import com.examen.spring.repository.EmploiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by dymama
 */
@RestController
@RequestMapping("/api")
public class EmploiController {

    @Autowired
    EmploiRepository emploiRepository;

    @GetMapping("/emplois")
    public List<Emploi> getAllEmmplois() {
        return emploiRepository.findAll();
    }

    @PostMapping("/emplois")
    public Emploi createEmploi(@Valid @RequestBody Emploi emploi) {
    	
    	
        return emploiRepository.save(emploi);
    }

    @GetMapping("/emplois/{id}")
    public Emploi getEmploiById(@PathVariable(value = "id") Long emploiId) {
        return emploiRepository.findById(emploiId)
                .orElseThrow(() -> new ResourceNotFoundException("Emploi:", "id", emploiId));
    }

    @PutMapping("/emplois/{id}")
    public Emploi updateEmploi(@PathVariable(value = "id") Long emploiId,
                                           @Valid @RequestBody Emploi emploiDetails) {

        Emploi emploi = emploiRepository.findById(emploiId)
                .orElseThrow(() -> new ResourceNotFoundException("Emploi", "id", emploiId));

        emploi.setLibelle(emploiDetails.getLibelle());
        Emploi updatedEmploi = emploiRepository.save(emploi);
        return updatedEmploi;
    }

    @DeleteMapping("/emplois/{id}")
    public ResponseEntity<?> deleteEmploi(@PathVariable(value = "id") Long emploiId) {
        Emploi emploi = emploiRepository.findById(emploiId)
                .orElseThrow(() -> new ResourceNotFoundException("Emploi", "id", emploiId));

        emploiRepository.delete(emploi);

        return ResponseEntity.ok().build();
    }
}
