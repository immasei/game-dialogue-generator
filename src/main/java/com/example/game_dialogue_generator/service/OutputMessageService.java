package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.repository.OutputMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OutputMessageService {

    @Autowired
    private OutputMessageRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<OutputMessageDTO> findOutputMessageByIdAndUserId(long id, int userId) {
        return repository.findOutputMessageByIdAndUserId(id, userId).map(this::convertToDTO);
    }

    public List<OutputMessageDTO> findOutputMessageByUserId(int userid) {
        return repository.findOutputMessageByUserId(userid).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // create
    public OutputMessageDTO createOutputMessage(OutputMessageDTO outputMessageDTO) {
        OutputMessage outputMessage = convertToModel(outputMessageDTO);
        OutputMessage createdOutputMessage = repository.save(outputMessage);
        return convertToDTO(createdOutputMessage);
    }

    // get by id
    public Optional<OutputMessageDTO> getOutputMessageById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    // get all
//    public List<OutputMessageDTO> getAllOutputMessages() {
//        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }

    // update
    public OutputMessageDTO updateOutputMessage(Long id, OutputMessageDTO updatedOutputMessageDTO) {
        Optional<OutputMessage> existingMessage = repository.findById(id);
        if (existingMessage.isPresent()) {
            OutputMessage updatedOutputMessage = convertToModel(updatedOutputMessageDTO);
            updatedOutputMessage.setId(id);
            OutputMessage savedOutputMessage = repository.save(updatedOutputMessage);
            return convertToDTO(savedOutputMessage);
        }
        return null; // placeholder, might add an exception in the future if I remember
    }

    // delete
    public void deleteOutputMessage(Long id) {
        repository.deleteById(id);
    }

    // Convert OutputMessage model to DTO
//    private OutputMessageDTO convertToDTO(OutputMessage outputMessage) {
//        OutputMessageDTO dto = new OutputMessageDTO();
//        dto.setId(outputMessage.getId());
//        dto.setDepth1(outputMessage.getDepth1());
//        dto.setDepth2_1(outputMessage.getDepth2_1());
//        dto.setDepth2_2(outputMessage.getDepth2_2());
//        dto.setDepth2_3(outputMessage.getDepth2_3());
//        dto.setDepth3_1_1(outputMessage.getDepth3_1_1());
//        dto.setDepth3_1_2(outputMessage.getDepth3_1_2());
//        dto.setDepth3_1_3(outputMessage.getDepth3_1_3());
//        dto.setDepth3_2_1(outputMessage.getDepth3_2_1());
//        dto.setDepth3_2_2(outputMessage.getDepth3_2_2());
//        dto.setDepth3_2_3(outputMessage.getDepth3_2_3());
//        dto.setDepth3_3_1(outputMessage.getDepth3_3_1());
//        dto.setDepth3_3_2(outputMessage.getDepth3_3_2());
//        dto.setDepth3_3_3(outputMessage.getDepth3_3_3());
//        dto.setUserId(outputMessage.getUserId());
//        return dto;
//    }

    // Convert DTO to OutputMessage model
//    public OutputMessage convertToModel(OutputMessageDTO dto) {
//        OutputMessage outputMessage = new OutputMessage();
//        outputMessage.setId(dto.getId());
//        outputMessage.setDepth1(dto.getDepth1());
//        outputMessage.setDepth2_1(dto.getDepth2_1());
//        outputMessage.setDepth2_2(dto.getDepth2_2());
//        outputMessage.setDepth2_3(dto.getDepth2_3());
//        outputMessage.setDepth3_1_1(dto.getDepth3_1_1());
//        outputMessage.setDepth3_1_2(dto.getDepth3_1_2());
//        outputMessage.setDepth3_1_3(dto.getDepth3_1_3());
//        outputMessage.setDepth3_2_1(dto.getDepth3_2_1());
//        outputMessage.setDepth3_2_2(dto.getDepth3_2_2());
//        outputMessage.setDepth3_2_3(dto.getDepth3_2_3());
//        outputMessage.setDepth3_3_1(dto.getDepth3_3_1());
//        outputMessage.setDepth3_3_2(dto.getDepth3_3_2());
//        outputMessage.setDepth3_3_3(dto.getDepth3_3_3());
//        outputMessage.setUserId(dto.getUserId());
//        return outputMessage;
//    }
    public OutputMessageDTO convertToDTO(OutputMessage outputMessage) {
        return modelMapper.map(outputMessage, OutputMessageDTO.class);
    }

    public OutputMessage convertToModel(OutputMessageDTO outputMessageDTO) {
        return modelMapper.map(outputMessageDTO, OutputMessage.class);
    }
}