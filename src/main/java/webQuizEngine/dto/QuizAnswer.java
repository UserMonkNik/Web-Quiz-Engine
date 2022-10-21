package webQuizEngine.dto;

public class QuizAnswer {
    private int[] answer;

    public QuizAnswer(int[] answer) {
        this.answer = answer;
    }
    public QuizAnswer() {}

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
