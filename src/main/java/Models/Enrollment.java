package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enrollment {
    private int idEnrollment;
    private Student student;
    private Course course;

    public Enrollment(Course course, Student student, int idEnrollment) {
        this.course = course;
        this.student = student;
        this.idEnrollment = idEnrollment;
    }
}
