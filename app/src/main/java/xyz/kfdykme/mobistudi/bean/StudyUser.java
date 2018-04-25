package xyz.kfdykme.mobistudi.bean;

/**
 * @author kf
 * @date 5/26/17
 * 用户bean
 */

public class StudyUser {

    private String id;
    /**
     * 0 -- parent 1 -- teacher 2 -- student
     */
    private int type;
    private String userName;
    private String passWord;
    private int learnedDay;
    /**
     * 学习的单元
     */
    private int learnProgress;
    /**
     * 学习的知识小节
     */
    private int learnCourse;
    private String catTheat;
    private String lastCatTheat;
    private String singInDate;
    private int learnCourseQuestion;
    public static int TYPE_NORMAL = 3;
    public static final int TYPE_STU = 0;
    public static final int TYPE_PAR = 1;
    public static final int TYPE_TEA = 2;


    public StudyUser(String id, int type, String userName, String passWord, int learnedDay, int learnProgress, int learnCourse, String catTheat, String lastCatTheat, String singInDate, int learnCourseQuestion) {
        this.id = id;
        this.type = type;
        this.userName = userName;
        this.passWord = passWord;
        this.learnedDay = learnedDay;
        this.learnProgress = learnProgress;
        this.learnCourse = learnCourse;
        this.catTheat = catTheat;
        this.lastCatTheat = lastCatTheat;
        this.singInDate = singInDate;
        this.learnCourseQuestion = learnCourseQuestion;
    }

    public int getLearnCourseQuestion() {
        return learnCourseQuestion;
    }

    public void setLearnCourseQuestion(int learnCourseQuestion) {
        this.learnCourseQuestion = learnCourseQuestion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getLearnedDay() {
        return learnedDay;
    }

    public void setLearnedDay(int learnedDay) {
        this.learnedDay = learnedDay;
    }

    public int getLearnProgress() {
        return learnProgress;
    }

    public void setLearnProgress(int learnProgress) {
        this.learnProgress = learnProgress;
    }

    public int getLearnCourse() {
        return learnCourse;
    }

    public void setLearnCourse(int learnCourse) {
        this.learnCourse = learnCourse;
    }

    public String getCatTheat() {
        return catTheat;
    }

    public void setCatTheat(String catTheat) {
        this.catTheat = catTheat;
    }

    public String getLastCatTheat() {
        return lastCatTheat;
    }

    public void setLastCatTheat(String lastCatTheat) {
        this.lastCatTheat = lastCatTheat;
    }

    public String getSingInDate() {
        return singInDate;
    }

    public void setSingInDate(String singInDate) {
        this.singInDate = singInDate;
    }
}
