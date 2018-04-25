package xyz.kfdykme.mobistudi.model;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.MainActivity;
import xyz.kfdykme.mobistudi.bean.Answer;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.bean.StudyModule;
import xyz.kfdykme.mobistudi.bean.StudyQuestion;
import xyz.kfdykme.mobistudi.bean.StudySubject;

/**
 *
 * @author kf
 * @date 2017/6/5
 */

public class BasicModel {
    List<StudyModule> result;

    List<StudySubject> subject;

    List<StudyCourse> course;

    List<StudyQuestion> question;

    List<Answer> answer;

    public List<StudyModule> getResult() {
        return result;
    }

    public void setResult(List<StudyModule> result) {
        this.result = result;
    }

    public List<StudySubject> getSubject() {
        return subject;
    }

    public void setSubject(List<StudySubject> subject) {
        this.subject = subject;
    }

    public List<StudyCourse> getCourse() {
        return course;
    }

    public void setCourse(List<StudyCourse> course) {
        this.course = course;
    }

    public List<StudyQuestion> getQuestion() {
        return question;
    }

    public void setQuestion(List<StudyQuestion> question) {
        this.question = question;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public static void fixData(){
        fixModule();
        fixSubject();
        fixCourse();
//        fixQuestion();
    }

    public static void fixModule(){
        for(StudyModule sM : MainActivity.localData.getLocalModules())
        {
            sM.setStudySubjects(MainActivity.localData.findStudySubject(sM.getStudySubjectsId()));
        }
    }

    public  static void fixSubject(){
        for (StudySubject sS: MainActivity.localData.getLocalSubjects()){
            sS.setStudyCourses(MainActivity.localData.findStudyCourse(sS.getStudyCoursesId()));
        }
    }

    public  static void fixCourse(){
        for (StudyCourse sC: MainActivity.localData.getLocalCourses()){
            //sC.setQuestions(MainActivity.localData.findStudyQuestion(sC.getQuestionsId()));
            sC.setNexCourses(MainActivity.localData.findStudyCourse(sC.getNexCoursesId()));
            sC.setPreCourses(MainActivity.localData.findStudyCourse(sC.getPreCoursesId()));
        }
    }

//    public  static void fixQuestion(){
//        for (StudyQuestion sQ:MainActivity.localData.getLocalQuestion()){
//            sQ.setSeletableAnswers(MainActivity.localData.findAnswer(sQ.getSeletableAnswersId()));
//            List<String> cAnswer = new ArrayList<String>();
//            cAnswer.add(sQ.getCorrectAnswerId());
//            sQ.setCorrectAnswer(MainActivity.localData.findAnswer(cAnswer).get(0));
//        }
//    }
}
