package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
    private int courseId;
    private String courseName;
    private Teacher teacher;

    public Course(Teacher teacher, String courseName, int courseId) {
        this.teacher = teacher;
        this.courseName = courseName;
        this.courseId = courseId;
    }
}
