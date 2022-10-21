package webQuizEngine.controller;

import webQuizEngine.entity.Quiz;
import webQuizEngine.dto.QuizAnswer;
import webQuizEngine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<?> createQuiz(Authentication authentication, @Valid @RequestBody Quiz quiz) {
        return quizService.createQuiz(authentication.getName(), quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable int id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/myQuizzes")
    public ResponseEntity<?> getAllQuizzesFromCurrentUser(Authentication auth) {
        return quizService.getAllQuizzesFromCurrentUser(auth.getName());
    }

    @GetMapping
    public ResponseEntity<?> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return quizService.getAllQuizzes(page, pageSize, sortBy);
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedQuizzes(Authentication auth,
                                                 @RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return quizService.getCompletedQuizzes(auth.getName(), page, pageSize);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<?> postQuiz(Authentication auth, @PathVariable int id, @RequestBody QuizAnswer quizAnswer) {
        return quizService.postQuiz(auth.getName(), id, quizAnswer.getAnswer());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuizById(Authentication auth, @Valid @RequestBody Quiz quiz) {
        return quizService.updateQuizById(auth.getName(), quiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuizById(Authentication auth, @PathVariable int id) {
        return quizService.deleteQuizById(auth.getName(), id);
    }
}
