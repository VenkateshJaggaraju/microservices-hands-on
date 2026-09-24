package com.quiz_service.quiz_service.mapper;

import com.quiz_service.quiz_service.dto.QuestionRequestDTO;
import com.quiz_service.quiz_service.dto.QuestionResponseDTO;
import com.quiz_service.quiz_service.model.Questions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    // Entity <- DTO
    @Mapping(target = "id", ignore = true)
    Questions toEntity(QuestionRequestDTO dto);


    // DTO <-- Entity
    QuestionResponseDTO toResponseDTO(Questions question);

}
