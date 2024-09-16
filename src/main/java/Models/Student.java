package Models;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Student {
    public int studentId;
    private String lastName;
    private String firstName;
    private LocalDateTime dateOfBirth;
    private String studentEmail;
    private String phoneNumber;
    private LocalDateTime enrollmentDate;
    private AcademicYear academicYear;

    public Student(int studentId, String lastName, String firstName, LocalDateTime dateOfBirth, String studentEmail, String phoneNumber, LocalDateTime enrollmentDate, AcademicYear academicYear) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.studentEmail = studentEmail;
        this.phoneNumber = phoneNumber;
        this.enrollmentDate = enrollmentDate;
        this.academicYear = academicYear;
    }
}
