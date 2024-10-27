package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.repository.OutputMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OutputMessageServiceTest {

    @Mock
    private OutputMessageRepository repository;

    @InjectMocks
    private OutputMessageService outputMessageService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
//        outputMessageService = new OutputMessageService(repository, modelMapper);  // Ensure modelMapper is injected here
    }

    /**
    @Test
    void testFindOutputMessageByIdAndUserId_Success() {
        OutputMessage outputMessage = new OutputMessage();
        outputMessage.setId(1L);
        outputMessage.setUserId(1);
        when(repository.findOutputMessageByIdAndUserId(1L, 1)).thenReturn(Optional.of(outputMessage));

        Optional<OutputMessageDTO> result = outputMessageService.findOutputMessageByIdAndUserId(1L, 1);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals(1, result.get().getUserId());
        verify(repository, times(1)).findOutputMessageByIdAndUserId(1L, 1);
    }**/

    @Test
    void testFindOutputMessageByIdAndUserId_NotFound() {
        when(repository.findOutputMessageByIdAndUserId(1L, 1)).thenReturn(Optional.empty());

        Optional<OutputMessageDTO> result = outputMessageService.findOutputMessageByIdAndUserId(1L, 1);

        assertFalse(result.isPresent());
        verify(repository, times(1)).findOutputMessageByIdAndUserId(1L, 1);
    }

    /**
    @Test
    void testFindOutputMessageByUserId_Success() {
        OutputMessage outputMessage = new OutputMessage();
        outputMessage.setId(1L);
        outputMessage.setUserId(1);
        when(repository.findOutputMessageByUserId(1)).thenReturn(List.of(outputMessage));

        List<OutputMessageDTO> result = outputMessageService.findOutputMessageByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(repository, times(1)).findOutputMessageByUserId(1);
    }**/

    @Test
    void testFindOutputMessageByUserId_NotFound() {
        when(repository.findOutputMessageByUserId(1)).thenReturn(List.of());

        List<OutputMessageDTO> result = outputMessageService.findOutputMessageByUserId(1);

        assertNotNull(result);
        assertEquals(0, result.size()); // Empty list
        verify(repository, times(1)).findOutputMessageByUserId(1);
    }

    /**
    @Test
    void testCreateOutputMessage_Success() {
        OutputMessageDTO dto = new OutputMessageDTO();
        dto.setUserId(1);

        OutputMessage outputMessage = new OutputMessage();
        outputMessage.setId(1L);
        outputMessage.setUserId(1);

        when(repository.save(any(OutputMessage.class))).thenReturn(outputMessage);

        OutputMessageDTO result = outputMessageService.createOutputMessage(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(OutputMessage.class));
    }**/

    /**
    @Test
    void testGetOutputMessageById_Success() {
        OutputMessage outputMessage = new OutputMessage();
        outputMessage.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(outputMessage));

        Optional<OutputMessageDTO> result = outputMessageService.getOutputMessageById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository, times(1)).findById(1L);
    }**/

    @Test
    void testGetOutputMessageById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<OutputMessageDTO> result = outputMessageService.getOutputMessageById(1L);

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(1L);
    }

    /**
    @Test
    void testGetAllOutputMessages_Success() {
        OutputMessage outputMessage = new OutputMessage();
        outputMessage.setId(1L);
        when(repository.findAll()).thenReturn(List.of(outputMessage));

        List<OutputMessageDTO> result = outputMessageService.getAllOutputMessages();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(repository, times(1)).findAll();
    }**/

    @Test
    void testGetAllOutputMessages_Empty() {
        when(repository.findAll()).thenReturn(List.of());

        List<OutputMessageDTO> result = outputMessageService.getAllOutputMessages();

        assertNotNull(result);
        assertEquals(0, result.size()); // Should return an empty list
        verify(repository, times(1)).findAll();
    }

    /**
    @Test
    void testUpdateOutputMessage_Success() {
        OutputMessage existingMessage = new OutputMessage();
        existingMessage.setId(1L);

        OutputMessage updatedMessage = new OutputMessage();
        updatedMessage.setId(1L);

        OutputMessageDTO updatedDTO = new OutputMessageDTO();
        updatedDTO.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existingMessage));
        when(repository.save(any(OutputMessage.class))).thenReturn(updatedMessage);

        OutputMessageDTO result = outputMessageService.updateOutputMessage(1L, updatedDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(OutputMessage.class));
    }**/

    @Test
    void testUpdateOutputMessage_NotFound() {
        OutputMessageDTO updatedDTO = new OutputMessageDTO();
        updatedDTO.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        OutputMessageDTO result = outputMessageService.updateOutputMessage(1L, updatedDTO);

        assertNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(OutputMessage.class));
    }

    @Test
    void testDeleteOutputMessage_Success() {
        doNothing().when(repository).deleteById(1L);

        outputMessageService.deleteOutputMessage(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOutputMessage_NotFound() {
        doThrow(new NoSuchElementException("OutputMessage not found")).when(repository).deleteById(1L);

        assertThrows(NoSuchElementException.class, () -> outputMessageService.deleteOutputMessage(1L));

        verify(repository, times(1)).deleteById(1L);
    }
}
