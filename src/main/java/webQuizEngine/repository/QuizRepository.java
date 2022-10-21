package webQuizEngine.repository;

import webQuizEngine.entity.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Integer> {
}
