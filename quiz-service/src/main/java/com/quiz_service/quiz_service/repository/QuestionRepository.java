package com.quiz_service.quiz_service.repository;

import com.quiz_service.quiz_service.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Questions, Long> {

    List<Questions> findByCategoryIgnoreCase(String category);

//    @Query(value = "SELECT q FROM Questions q WHERE q.category LIKE %:category%")
    @Query(value = "SELECT * FROM questions WHERE category ILIKE CONCAT('%', :category, '%')", nativeQuery = true)
    List<Questions> findByCategoryLike(@Param("category") String category);

    @Query(value = "SELECT * FROM questions  WHERE category= :category ORDER BY RANDOM() LIMIT :numQ",
            nativeQuery = true)
    List<Questions> findRandomQuestionsByCategory(@Param("category") String category, @Param("numQ") int numQ);
}
