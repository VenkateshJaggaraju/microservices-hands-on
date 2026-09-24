package com.question_service.question_service.controller;

import com.question_service.question_service.dto.QuestionRequestDTO;
import com.question_service.question_service.dto.QuestionResponseDTO;
import com.question_service.question_service.dto.QuizResponseDTO;
import com.question_service.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/question")
    public QuestionResponseDTO addQuestion(@RequestBody QuestionRequestDTO dto){
        return questionService.addQuestion(dto);
    }

    @GetMapping("/question")
    public ResponseEntity<Map<String, Object>> getQuestions(){
        return questionService.getQuestions();

    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterByCategory(@RequestParam String category){
        return questionService.filterByCategory(category);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
                                                            @RequestParam Integer numQ){
        return questionService.getQuestionsForQuiz(category, numQ);
    }

    @PostMapping("/get-questions")
    public ResponseEntity<Map<String, List<QuestionResponseDTO>>> getQuestionsFromId(@RequestBody List<Long> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("/get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponseDTO> responses){
        return questionService.getScore(responses);
    }
}
