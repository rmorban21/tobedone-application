package com.ToBeDone.app.ToBeDoneSpringApp.service;

import com.ToBeDone.app.ToBeDoneSpringApp.entities.Status;
import com.ToBeDone.app.ToBeDoneSpringApp.entities.ToBeDone;
import com.ToBeDone.app.ToBeDoneSpringApp.exception.ResourceNotFoundException;
import com.ToBeDone.app.ToBeDoneSpringApp.repository.ToBeDoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ToBeDoneServiceTest {

    @Mock
    private ToBeDoneRepository toBeDoneRepository;

    @InjectMocks
    private ToBeDoneService toBeDoneService;

    private ToBeDone toBeDone1;
    private ToBeDone toBeDone2;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        toBeDone1 = new ToBeDone();
        toBeDone1.setId(UUID.randomUUID());
        toBeDone1.setTitle("Test ToDo 1");
        toBeDone1.setDescription("Description 1");
        toBeDone1.setStatus(Status.PENDING);
        toBeDone1.setCreatedDate(LocalDateTime.now());
        toBeDone1.setUserId(userId);

        toBeDone2 = new ToBeDone();
        toBeDone2.setId(UUID.randomUUID());
        toBeDone2.setTitle("Test ToDo 2");
        toBeDone2.setDescription("Description 2");
        toBeDone2.setStatus(Status.PENDING);
        toBeDone2.setCreatedDate(LocalDateTime.now());
        toBeDone2.setUserId(userId);
    }

    @Test
    public void testGetAllToBeDones() {
        when(toBeDoneRepository.findByUserId(userId)).thenReturn(Arrays.asList(toBeDone1, toBeDone2));
        List<ToBeDone> toBeDones = toBeDoneService.getAllToBeDones(userId);
        assertEquals(2, toBeDones.size());
        verify(toBeDoneRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testCreateToBeDone() {
        when(toBeDoneRepository.save(toBeDone1)).thenReturn(toBeDone1);
        ToBeDone createdToBeDone = toBeDoneService.createToBeDone(toBeDone1);
        assertEquals(toBeDone1.getTitle(), createdToBeDone.getTitle());
        verify(toBeDoneRepository, times(1)).save(toBeDone1);
    }

    @Test
    public void testUpdateToBeDone() {
        UUID id = toBeDone1.getId();
        when(toBeDoneRepository.findById(id)).thenReturn(Optional.of(toBeDone1));
        when(toBeDoneRepository.save(toBeDone1)).thenReturn(toBeDone1);

        ToBeDone updatedToBeDone = toBeDoneService.updateToBeDone(id, toBeDone1);
        assertEquals(toBeDone1.getTitle(), updatedToBeDone.getTitle());
        verify(toBeDoneRepository, times(1)).findById(id);
        verify(toBeDoneRepository, times(1)).save(toBeDone1);
    }

    @Test
    public void testUpdateToBeDoneStatus() {
        UUID id = toBeDone1.getId();
        when(toBeDoneRepository.findById(id)).thenReturn(Optional.of(toBeDone1));
        when(toBeDoneRepository.save(toBeDone1)).thenReturn(toBeDone1);

        ToBeDone updatedToBeDone = toBeDoneService.updateToBeDoneStatus(id, Status.COMPLETED);
        assertEquals(Status.COMPLETED, updatedToBeDone.getStatus());
        verify(toBeDoneRepository, times(1)).findById(id);
        verify(toBeDoneRepository, times(1)).save(toBeDone1);
    }

    @Test
    public void testDeleteToBeDone() {
        UUID id = toBeDone1.getId();
        when(toBeDoneRepository.findById(id)).thenReturn(Optional.of(toBeDone1));
        doNothing().when(toBeDoneRepository).delete(toBeDone1);

        toBeDoneService.deleteToBeDone(id);
        verify(toBeDoneRepository, times(1)).findById(id);
        verify(toBeDoneRepository, times(1)).delete(toBeDone1);
    }

    @Test
    public void testUpdateToBeDoneNotFound() {
        UUID id = UUID.randomUUID();
        when(toBeDoneRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            toBeDoneService.updateToBeDone(id, toBeDone1);
        });
        verify(toBeDoneRepository, times(1)).findById(id);
    }
}