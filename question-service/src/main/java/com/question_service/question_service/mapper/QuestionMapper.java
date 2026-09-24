package com.question_service.question_service.mapper;

import com.question_service.question_service.dto.QuestionRequestDTO;
import com.question_service.question_service.dto.QuestionResponseDTO;
import com.question_service.question_service.model.Questions;
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
