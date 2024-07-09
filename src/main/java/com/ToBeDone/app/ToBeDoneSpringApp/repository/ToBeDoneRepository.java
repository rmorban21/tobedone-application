package com.ToBeDone.app.ToBeDoneSpringApp.repository;

import com.ToBeDone.app.ToBeDoneSpringApp.entities.ToBeDone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ToBeDoneRepository extends JpaRepository<ToBeDone, UUID> {
    List<ToBeDone> findByUserId(UUID userId);
}