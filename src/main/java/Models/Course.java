package Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Course {
    private int courseId;
    private String courseName;
    private Teacher teacher;

    public Course(int courseId,String courseName,Teacher teacher) {
        this.courseId = courseId;
        this.teacher = teacher;
        this.courseName = courseName;

    }
}
