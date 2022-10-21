package webQuizEngine.service;

import webQuizEngine.dto.QuizResponse;
import webQuizEngine.entity.CompletedQuiz;
import webQuizEngine.entity.Quiz;
import webQuizEngine.entity.User;
import webQuizEngine.exception.QuizNotFoundException;
import webQuizEngine.repository.CompletedQuizRepository;
import webQuizEngine.repository.QuizRepository;
import webQuizEngine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class QuizService {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository, CompletedQuizRepository completedQuizRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completedQuizRepository = completedQuizRepository;
    }

    public ResponseEntity<?> createQuiz(String currentUser, Quiz quiz) {
        User user = userRepository.findById(currentUser).get();
        quiz.setUser(user);

        Quiz temp = quizRepository.save(quiz);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    public ResponseEntity<?> getQuizById(int id) {

        if (quizRepository.existsById(id)) {
            return new ResponseEntity<>(quizRepository.findById(id).get(), HttpStatus.OK);
        }
        throw new QuizNotFoundException("Quiz not found for id: " + id);
    }

    public ResponseEntity<?> getAllQuizzesFromCurrentUser(String currentUser) {
        User user = userRepository.findById(currentUser).get();
        return new ResponseEntity<>(user.getQuizzes(), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllQuizzes(Integer page, Integer pageSize, String sortBy) {

        PageRequest paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        Page<Quiz> pageResult = quizRepository.findAll(paging);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);

    }

    public ResponseEntity<?> getCompletedQuizzes(String currentUser, Integer page, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<CompletedQuiz> completedQuizzes = completedQuizRepository.findAllCompletedQuizzes(currentUser, pageRequest);

        return new ResponseEntity<>(completedQuizzes, HttpStatus.OK);
    }

    public ResponseEntity<?> postQuiz(String currentUser, int id, int[] answer) {

        if (quizRepository.existsById(id)) {

            if (isRightAnswer(id, answer)) {
                addToCompletedQuizzes(currentUser, id);

                return new ResponseEntity<>(
                        new QuizResponse(true, "Congratulations, you're right!"),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new QuizResponse(false, "Wrong answer! Please,  try again."),
                        HttpStatus.OK);
            }

        }
        throw new QuizNotFoundException("Quiz not found for id: " + id);
    }

    public ResponseEntity<?> updateQuizById(String currentUser, Quiz quiz) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quiz.getId());

        if (optionalQuiz.isPresent()) {

            if (optionalQuiz.get().getUser().getEmail().equals(currentUser)) {

                Quiz tempQuiz = optionalQuiz.get();
                tempQuiz.setTitle(quiz.getTitle());
                tempQuiz.setText(quiz.getText());
                tempQuiz.setOptions(quiz.getOptions());
                tempQuiz.setAnswer(quiz.getAnswer());

                quizRepository.save(tempQuiz);

                return new ResponseEntity<>(tempQuiz, HttpStatus.OK);
            } else {

                return new ResponseEntity<>("You have no access to update quiz with id: " + quiz.getId(), HttpStatus.FORBIDDEN);
            }
        }

        throw new QuizNotFoundException(String.format("Quiz with id: %d does not exist.", quiz.getId()));
    }

    public ResponseEntity<?> deleteQuizById(String currentUser, int id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);

        if (optionalQuiz.isPresent()) {

            if (optionalQuiz.get().getUser().getEmail().equals(currentUser)) {

                quizRepository.deleteById(id);
                return new ResponseEntity<>(String.format("Quiz with id: %d have been removed", id), HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>("You have no access to delete quiz with id: " + id, HttpStatus.FORBIDDEN);
            }

        }

        throw new QuizNotFoundException(String.format("Quiz with id: %d not exist.", id));
    }

    private void addToCompletedQuizzes(String currentUser, int id) {
        User user = userRepository.findById(currentUser).get();
        CompletedQuiz completedQuiz = new CompletedQuiz(id, LocalDateTime.now());

        user.getCompletedQuizzes().add(completedQuiz);

        userRepository.save(user);
    }

    private boolean isRightAnswer(int id, int[] answer) {
        int[] sortedUserAnswer = Arrays.stream(answer).sorted().toArray();
        int[] sortedQuizAnswer = Arrays.stream(quizRepository.findById(id).get().getAnswer()).sorted().toArray();
        return Arrays.equals(sortedQuizAnswer, sortedUserAnswer);
    }
}
