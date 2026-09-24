package com.quiz_service.quiz_service.controller;


import com.quiz_service.quiz_service.dto.QuestionResponseDTO;
import com.quiz_service.quiz_service.dto.QuizResponseDTO;
import com.quiz_service.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                             @RequestParam int numQ,
                                             @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("/questions")
    public ResponseEntity<Map<String, List<QuestionResponseDTO>>> getQuestions(@RequestParam Long id){
        return quizService.getQuestions(id);
    }

    @PostMapping("/submit")
    public  ResponseEntity<Integer> calculateResult(@RequestParam Long id, @RequestBody List<QuizResponseDTO> responses){
        return quizService.calculateResult(id, responses);
    }
}
