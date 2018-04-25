package xyz.kfdykme.mobistudi.eventbus;

import xyz.kfdykme.mobistudi.bean.StudySubject;

/**
 * @author kf
 * @date 5/26/17
 */

public class CourseUnitMapEvent {

    StudySubject subject;

    public CourseUnitMapEvent(StudySubject subject) {
        this.subject = subject;
    }

    public StudySubject getSubject() {
        return subject;
    }

    public void setSubject(StudySubject subject) {
        this.subject = subject;
    }
}
