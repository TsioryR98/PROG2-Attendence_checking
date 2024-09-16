package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
    private int courseId;
    private String courseName;
    private Teacher idTeacherFK;

    public Course(Teacher idTeacherFK, String courseName, int courseId) {
        this.idTeacherFK = idTeacherFK;
        this.courseName = courseName;
        this.courseId = courseId;
    }
}
