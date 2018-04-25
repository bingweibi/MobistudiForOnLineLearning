package xyz.kfdykme.mobistudi.bean;

import java.util.List;

/**
 * @author kf
 * @date 5/26/17
 */

public class StudyQuestion {

    String id;

    String description;

    List<String> seletableAnswersId;

    String correctAnswerId;

    List<Answer> seletableAnswers;

    Answer correctAnswer;

    public static int DEFAULT_WEIGHT = 1;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    int weight = DEFAULT_WEIGHT;

    public StudyQuestion(String id, String description, List<String> seletableAnswersId, String correctAnswerId) {
        this.id = id;
        this.description = description;
        this.seletableAnswersId = seletableAnswersId;
        this.correctAnswerId = correctAnswerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSeletableAnswersId() {
        return seletableAnswersId;
    }

    public void setSeletableAnswersId(List<String> seletableAnswersId) {
        this.seletableAnswersId = seletableAnswersId;
    }

    public String getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(String correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public List<Answer> getSeletableAnswers() {
        return seletableAnswers;
    }

    public void setSeletableAnswers(List<Answer> seletableAnswers) {
        this.seletableAnswers = seletableAnswers;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "StudyQuestion{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", seletableAnswersId=" + seletableAnswersId +
                ", correctAnswerId='" + correctAnswerId + '\'' +
                ", seletableAnswers=" + seletableAnswers +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
