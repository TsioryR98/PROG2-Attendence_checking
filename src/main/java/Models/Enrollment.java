package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enrollment {
    private int idEnrollment;
    private Student idStudentFK;
    private Course idCourse;

    public Enrollment(Course idCourse, Student idStudentFK, int idEnrollment) {
        this.idCourse = idCourse;
        this.idStudentFK = idStudentFK;
        this.idEnrollment = idEnrollment;
    }
}
