package webQuizEngine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CompletedQuizzes")
public class CompletedQuiz {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long uniqueId;
    private int id;
    private LocalDateTime completedAt;

    public CompletedQuiz() {

    }

    public CompletedQuiz(int id, LocalDateTime completedAt) {
        this.id = id;
        this.completedAt = completedAt;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    @Override
    public String toString() {
        return "CompletedQuiz{" +
                "id=" + id +
                ", completedAt=" + completedAt +
                '}';
    }
}
