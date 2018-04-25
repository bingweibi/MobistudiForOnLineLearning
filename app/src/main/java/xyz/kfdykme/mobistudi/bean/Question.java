package xyz.kfdykme.mobistudi.bean;

/**
 * @author bbw
 * @date 2017/12/4
 */

public class Question {

    private int id;
    private int courseID;
    private String courseName;
    private String course;
    private String description;
    private String correct;
    private String a;
    private String b;
    private String c;
    private String d;
    private String explain;

    public Question(int id, int courseID, String courseName, String course, String description, String correct, String a, String b, String c, String d, String explain) {
        this.id = id;
        this.courseID = courseID;
        this.courseName = courseName;
        this.course = course;
        this.description = description;
        this.correct = correct;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.explain = explain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
