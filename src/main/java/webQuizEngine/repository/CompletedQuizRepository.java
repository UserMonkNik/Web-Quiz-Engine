package webQuizEngine.repository;

import webQuizEngine.entity.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizRepository extends CrudRepository<CompletedQuiz, Long> {
    @Query(value = "SELECT * FROM COMPLETED_QUIZZES WHERE USER_ID = :currentUser ORDER BY COMPLETED_AT DESC",
            countQuery = "SELECT count(*) FROM COMPLETED_QUIZZES WHERE USER_ID = :currentUser",
            nativeQuery = true)
    Page<CompletedQuiz> findAllCompletedQuizzes(@Param("currentUser") String currentUser, Pageable pageable);
}
