package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enrollment {
    private int idEnrollment;
    private Student student;
    private Course course;

    public Enrollment(int idEnrollment, Student student,Course course) {
        this.student = student;
        this.idEnrollment = idEnrollment;
        this.course = course;
    }
}
