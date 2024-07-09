package com.ToBeDone.app.ToBeDoneSpringApp.controller;

import com.ToBeDone.app.ToBeDoneSpringApp.entities.Status;
import com.ToBeDone.app.ToBeDoneSpringApp.entities.ToBeDone;
import com.ToBeDone.app.ToBeDoneSpringApp.service.ToBeDoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/tobedones")
public class ToBeDoneController {
    @Autowired
    private ToBeDoneService toBeDoneService;

    @GetMapping("/{userId}")
    public List<ToBeDone> getAllToBeDones(@PathVariable UUID userId) {
        return toBeDoneService.getAllToBeDones(userId);
    }

    @PostMapping
    public ToBeDone createToBeDone(@RequestBody ToBeDone toBeDone) {
        return toBeDoneService.createToBeDone(toBeDone);
    }

    @PutMapping("/{id}")
    public ToBeDone updateToBeDone(@PathVariable UUID id, @RequestBody ToBeDone toBeDoneDetails) {
        return toBeDoneService.updateToBeDone(id, toBeDoneDetails);
    }

    @PatchMapping("/{id}/status")
    public ToBeDone updateToBeDoneStatus(@PathVariable UUID id, @RequestBody Status status) {
        return toBeDoneService.updateToBeDoneStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToBeDone(@PathVariable UUID id) {
        toBeDoneService.deleteToBeDone(id);
        return ResponseEntity.ok().build();
    }
}