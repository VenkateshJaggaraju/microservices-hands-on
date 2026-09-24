package com.quiz_service.quiz_service.service;


import com.quiz_service.quiz_service.dto.QuestionResponseDTO;
import com.quiz_service.quiz_service.dto.QuizResponseDTO;
import com.quiz_service.quiz_service.feign.QuizFeign;
import com.quiz_service.quiz_service.model.Questions;
import com.quiz_service.quiz_service.model.Quiz;
import com.quiz_service.quiz_service.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizFeign quizFeign;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        ResponseEntity<List<Integer>> questionResponse = quizFeign.getQuestionsForQuiz(category, numQ);
        List<Integer> ids = questionResponse.getBody();

        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("No questions found for category: " + category);
        }

        List<Long> questionIds = ids.stream()
                .map(Integer::longValue)
                .toList();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);

        quizRepository.save(quiz);

        return ResponseEntity.status(201).body("Quiz created with id: " + quiz.getId());
    }

    public ResponseEntity<Map<String, List<QuestionResponseDTO>>> getQuestions(Long id) {

        Quiz quiz = quizRepository.findById(id).get();
        List<Long> questionIds = quiz.getQuestionIds();

        ResponseEntity<Map<String, List<QuestionResponseDTO>>> questions=quizFeign.getQuestionsFromId(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Long id, List<QuizResponseDTO> responses) {
//        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));

        ResponseEntity<Integer> score=quizFeign.getScore(responses);

        return score;
    }

}
