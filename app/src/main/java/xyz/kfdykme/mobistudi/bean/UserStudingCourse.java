package xyz.kfdykme.mobistudi.bean;

import java.util.List;

/**
 * @author cxf
 * @date 5/26/17
 */

public class UserStudingCourse extends StudyCourse {

    int correctQuestion;
    int wrongQuestion;

    public UserStudingCourse(String id, String title, String description, List<String> questionsId, List<String> preCoursesId, List<String> nexCoursesId, List<StudyQuestion> questions, List<StudyCourse> preCourses, List<StudyCourse> nexCourses, int correctQuestion, int wrongQuestion) {
        super(id, title, description, questionsId, preCoursesId, nexCoursesId);
        this.correctQuestion = correctQuestion;
        this.wrongQuestion = wrongQuestion;
    }

    public int getCorrectQuestion() {
        return correctQuestion;
    }

    public void setCorrectQuestion(int correctQuestion) {
        this.correctQuestion = correctQuestion;
    }

    public int getWrongQuestion() {
        return wrongQuestion;
    }

    public void setWrongQuestion(int wrongQuestion) {
        this.wrongQuestion = wrongQuestion;
    }

    @Override
    public String toString() {
        return "UserStudingCourse{" +
                "correctQuestion=" + correctQuestion +
                ", wrongQuestion=" + wrongQuestion +
                '}';
    }
}
