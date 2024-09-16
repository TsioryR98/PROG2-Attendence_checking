package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher {
    private int teacherId;
    private String lastName;
    private String firstName;
    private String contact;
    private String teacherAdress;

    public Teacher(int teacherId, String lastName, String firstName, String contact, String teacherAdress) {
        this.teacherId = teacherId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contact = contact;
        this.teacherAdress = teacherAdress;
    }
}
