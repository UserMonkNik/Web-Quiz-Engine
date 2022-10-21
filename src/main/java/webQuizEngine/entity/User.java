package webQuizEngine.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @Email(regexp = ".*@.*\\..*")
    @NotNull
    private String email;
    @Size(min = 5)
    private String password;
    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<CompletedQuiz> completedQuizzes = new ArrayList<>();

    public User() {
    }

    public List<CompletedQuiz> getCompletedQuizzes() {
        return completedQuizzes;
    }

    public void setCompletedQuizzes(List<CompletedQuiz> completedQuizzes) {
        this.completedQuizzes = completedQuizzes;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
