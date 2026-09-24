package com.quiz_service.quiz_service.feign;

import com.quiz_service.quiz_service.dto.QuestionResponseDTO;
import com.quiz_service.quiz_service.dto.QuizResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

//Connecting QuizService and QuestionService
@FeignClient("QUESTION-SERVICE")
public interface QuizFeign {

    // what're the things you'd like to use from QuestionService
    @GetMapping("/questions/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
                                                             @RequestParam Integer numQ);

    @PostMapping("/questions/get-questions")
    public ResponseEntity<Map<String, List<QuestionResponseDTO>>> getQuestionsFromId(@RequestBody List<Long> questionIds);

    @PostMapping("/questions/get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponseDTO> responses);

}
