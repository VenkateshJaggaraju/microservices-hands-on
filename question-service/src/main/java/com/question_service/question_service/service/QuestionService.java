package com.question_service.question_service.service;


import com.question_service.question_service.dto.QuestionRequestDTO;
import com.question_service.question_service.dto.QuestionResponseDTO;
import com.question_service.question_service.dto.QuizResponseDTO;
import com.question_service.question_service.mapper.QuestionMapper;
import com.question_service.question_service.model.Questions;
import com.question_service.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private Environment environment;

    static int count=0;

    public QuestionResponseDTO addQuestion(QuestionRequestDTO dto) {

        Questions question = questionMapper.toEntity(dto);
        questionRepository.save(question);
        return questionMapper.toResponseDTO(question);
    }

    public ResponseEntity<Map<String, Object>> getQuestions() {

        System.err.println(environment.getProperty("local.server.port")+" count: "+count);
        count++;
        Map<String, Object> responseBody = Map.of(
                "result", "data found",
                "data", questionRepository.findAll()
        );

        return ResponseEntity.status(200).body(responseBody);
    }

    public ResponseEntity<Map<String, Object>> filterByCategory(String category) {

        Map<String, Object> responseBody = Map.of(
                "result", "data found",
                "data", questionRepository.findByCategoryLike(category)
        );

        return ResponseEntity.status(201).body(responseBody);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions=questionRepository.findRandomQuestionsByCategory(category, numQ);
        return ResponseEntity.ok(questions);
    }

    public ResponseEntity<Map<String, List<QuestionResponseDTO>>> getQuestionsFromId(List<Long> questionIds) {
        List<QuestionResponseDTO> response=new ArrayList<>();
        List<Questions> questions=new ArrayList<>();

        for(Long questionId: questionIds){
            questions.add(questionRepository.findById(questionId).get());
        }

        for(Questions question: questions){
            QuestionResponseDTO dto=new QuestionResponseDTO(question.getId(),
                                                            question.getQuestionTitle(),
                                                            question.getOption1(),
                                                            question.getOption2(),
                                                            question.getOption3(),
                                                            question.getOption4()
            );
            response.add(dto);
        }

        return ResponseEntity.status(200).body(Map.of("response", response));
    }

    public ResponseEntity<Integer> getScore(List<QuizResponseDTO> responses) {

        int right = 0;

        for (QuizResponseDTO response : responses) {
            // Find the matching question from the database by converting both IDs to long values
            Questions question = questionRepository.findById(response.getId()).get();

            if (response.getRightAnswer().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return ResponseEntity.ok(right);
    }
}
