package webQuizEngine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @Size(min = 2)
    @NotNull
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer = new int[0];
    @ManyToOne()
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private User user;

    public Quiz(String title, String text, String[] options, int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }
    public Quiz() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
