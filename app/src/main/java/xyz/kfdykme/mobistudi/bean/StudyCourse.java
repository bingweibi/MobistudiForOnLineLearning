package xyz.kfdykme.mobistudi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kf
 * @date 5/26/17
 */

public class StudyCourse {

    private String id;
    private String title;
    private String description;
    private List<String> questionsId;
    private List<String> preCoursesId;
    private List<String> nexCoursesId;
    private List<StudyQuestion> questions;
    private List<StudyCourse> preCourses;
    private List<StudyCourse> nexCourses;
    private List<CourseDataPerUser> courseDataPerUsers = new ArrayList<CourseDataPerUser>();
    private int x ;
    private int y;

    public StudyCourse(String id, String title, String description, List<String> questionsId, List<String> preCoursesId, List<String> nexCoursesId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questionsId = questionsId;
        this.preCoursesId = preCoursesId;
        this.nexCoursesId = nexCoursesId;
    }

    public List<CourseDataPerUser> getCourseDataPerUsers() {
        return courseDataPerUsers;
    }

    public void setCourseDataPerUsers(List<CourseDataPerUser> courseDataPerUsers) {
        this.courseDataPerUsers = courseDataPerUsers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(List<String> questionsId) {
        this.questionsId = questionsId;
    }

    public List<String> getPreCoursesId() {
        return preCoursesId;
    }

    public void setPreCoursesId(List<String> preCoursesId) {
        this.preCoursesId = preCoursesId;
    }

    public List<String> getNexCoursesId() {
        return nexCoursesId;
    }

    public void setNexCoursesId(List<String> nexCoursesId) {
        this.nexCoursesId = nexCoursesId;
    }

    public List<StudyQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<StudyQuestion> questions) {
        this.questions = questions;
    }

    public List<StudyCourse> getPreCourses() {
        return preCourses;
    }

    public void setPreCourses(List<StudyCourse> preCourses) {
        this.preCourses = preCourses;
    }

    public List<StudyCourse> getNexCourses() {
        return nexCourses;
    }

    public void setNexCourses(List<StudyCourse> nexCourses) {
        this.nexCourses = nexCourses;
    }

    @Override
    public String toString() {
        return "StudyCourse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", questionsId=" + questionsId +
                ", preCoursesId=" + preCoursesId +
                ", nexCoursesId=" + nexCoursesId +

                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
