package com.ToBeDone.app.ToBeDoneSpringApp.service;
import com.ToBeDone.app.ToBeDoneSpringApp.entities.Status;
import com.ToBeDone.app.ToBeDoneSpringApp.entities.ToBeDone;
import com.ToBeDone.app.ToBeDoneSpringApp.exception.ResourceNotFoundException;
import com.ToBeDone.app.ToBeDoneSpringApp.repository.ToBeDoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ToBeDoneService {
    @Autowired
    private ToBeDoneRepository toBeDoneRepository;

    public List<ToBeDone> getAllToBeDones(UUID userId) {
        return toBeDoneRepository.findByUserId(userId);
    }

    public ToBeDone createToBeDone(ToBeDone toBeDone) {
        toBeDone.setCreatedDate(LocalDateTime.now());
        return toBeDoneRepository.save(toBeDone);
    }

    public ToBeDone updateToBeDone(UUID id, ToBeDone toBeDoneDetails) {
        ToBeDone toBeDone = toBeDoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToBeDone not found"));
        toBeDone.setTitle(toBeDoneDetails.getTitle());
        toBeDone.setDescription(toBeDoneDetails.getDescription());
        toBeDone.setStatus(toBeDoneDetails.getStatus());
        toBeDone.setDueDate(toBeDoneDetails.getDueDate());
        toBeDone.setPriority(toBeDoneDetails.getPriority());
        toBeDone.setCategory(toBeDoneDetails.getCategory());
        return toBeDoneRepository.save(toBeDone);
    }

    public ToBeDone updateToBeDoneStatus(UUID id, Status status) {
        ToBeDone toBeDone = toBeDoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToBeDone not found"));
        toBeDone.setStatus(status);
        return toBeDoneRepository.save(toBeDone);
    }

    public void deleteToBeDone(UUID id) {
        ToBeDone toBeDone = toBeDoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToBeDone not found"));
        toBeDoneRepository.delete(toBeDone);
    }
}