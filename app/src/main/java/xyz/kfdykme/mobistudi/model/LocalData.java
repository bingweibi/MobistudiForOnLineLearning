package xyz.kfdykme.mobistudi.model;

import java.util.ArrayList;
import java.util.List;

import xyz.kfdykme.mobistudi.bean.Answer;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.bean.StudyModule;
import xyz.kfdykme.mobistudi.bean.StudyQuestion;
import xyz.kfdykme.mobistudi.bean.StudySubject;

/**
 * @author kf
 * @date 2017/5/28
 */

public class LocalData {

    private List<StudyModule> localModules;
    private List<StudySubject> localSubjects;
    private List<StudyCourse> localCourses;
    private List<StudyQuestion> localQuestion;
    private List<Answer> localAnswer;

    public LocalData() {
    }

    public List<StudyModule> getLocalModules() {
        return localModules;
    }

    public void setLocalModules(List<StudyModule> localModules) {
        this.localModules = localModules;
    }

    public List<StudySubject> getLocalSubjects() {
        return localSubjects;
    }

    public void setLocalSubjects(List<StudySubject> localSubjects) {
        this.localSubjects = localSubjects;
    }

    public List<StudyCourse> getLocalCourses() {
        return localCourses;
    }

    public void setLocalCourses(List<StudyCourse> localCourses) {
        this.localCourses = localCourses;
    }
//
//    public List<StudyQuestion> getLocalQuestion() {
//        return localQuestion;
//    }
//
//    public void setLocalQuestion(List<StudyQuestion> localQuestion) {
//        this.localQuestion = localQuestion;
//    }
//
//    public List<Answer> getLocalAnswer() {
//        return localAnswer;
//    }
//
//    public void setLocalAnswer(List<Answer> localAnswer) {
//        this.localAnswer = localAnswer;
//    }

    public List<StudySubject> findStudySubject(List<String> ids){
        List<StudySubject> list = new ArrayList<StudySubject>();

        if(localSubjects != null && ids != null){
            for (StudySubject s: localSubjects){
                for(String id : ids){
                    if (s.getId().equals(id)){
                        list.add(s);
                    }
                }
            }
        }

        return list;
    }

    public List<StudyCourse> findStudyCourse(List<String> courseIds){
        List<StudyCourse> list = new ArrayList<StudyCourse>();
        if(localCourses != null && courseIds != null){
            for(StudyCourse c:localCourses){
                for (String id:courseIds){
                    if(c.getId().equals(id)){
                        list.add(c);
                    }
                }
            }
        }
        return  list;
    }

//    public List<StudyQuestion> findStudyQuestion(List<String> questionIds){
//        List<StudyQuestion> list = new ArrayList<StudyQuestion>();
//        if(localQuestion != null && questionIds != null){
//            for(StudyQuestion q:localQuestion){
//                for (String id:questionIds){
//                    if(q.getId().equals(id)){
//                        list.add(q);
//                    }
//                }
//            }
//        }
//        return  list;
//    }
//
//    public List<Answer> findAnswer(List<String> answerIds){
//        List<Answer> list = new ArrayList<Answer>();
//        if(localAnswer != null && answerIds != null){
//            for(Answer a : localAnswer){
//                for(String id:answerIds){
//                    if(a.getId().equals(id)){
//                        list.add(a);
//                    }
//                }
//            }
//        }
//        return list;
//    }

    public void saveCourse(StudyCourse course){
        for(int i =0; i < getLocalCourses().size();i++){
            if(getLocalCourses().get(i).getId().equals(course.getId())){
                getLocalCourses().set(i,course);
            }
        }
    }
}
