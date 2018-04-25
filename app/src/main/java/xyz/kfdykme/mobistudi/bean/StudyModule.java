package xyz.kfdykme.mobistudi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kf
 * @date 5/26/17
 */

public class StudyModule {

    private String id;
    private String title;
    private String description;
    private List<String> studySubjectsId;
    private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    public StudyModule(String id, String title, String description, List<String> studySubjectsId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.studySubjectsId = studySubjectsId;
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

    public List<String> getStudySubjectsId() {
        return studySubjectsId;
    }

    public void setStudySubjectsId(List<String> studySubjectsId) {
        this.studySubjectsId = studySubjectsId;
    }

    public List<StudySubject> getStudySubjects() {
        return studySubjects;
    }

    public void setStudySubjects(List<StudySubject> studySubjects) {
        this.studySubjects = studySubjects;
    }
}
