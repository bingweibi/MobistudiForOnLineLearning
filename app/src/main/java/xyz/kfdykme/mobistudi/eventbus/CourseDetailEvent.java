package xyz.kfdykme.mobistudi.eventbus;

import xyz.kfdykme.mobistudi.bean.StudyCourse;

/**
 * @author kf
 * @date 5/26/17
 */

public class CourseDetailEvent {

    private StudyCourse course;
    public CourseDetailEvent(StudyCourse course) {
        this.course = course;
    }
    public StudyCourse getCourse() {
        return course;
    }
    public void setCourse(StudyCourse course) {
        this.course = course;
    }
}
