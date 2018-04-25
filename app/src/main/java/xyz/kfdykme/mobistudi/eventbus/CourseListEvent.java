package xyz.kfdykme.mobistudi.eventbus;

import java.util.List;

import xyz.kfdykme.mobistudi.bean.StudyModule;

/**
 * @author kf
 * @date 5/26/17
 */

public class CourseListEvent {

    List<StudyModule> studyModules;

    public CourseListEvent(List<StudyModule> studyModules) {
        this.studyModules = studyModules;
    }

    public List<StudyModule> getStudyModules() {
        return studyModules;
    }

    public void setStudyModules(List<StudyModule> studyModules) {
        this.studyModules = studyModules;
    }
}
