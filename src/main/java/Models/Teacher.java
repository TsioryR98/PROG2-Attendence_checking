package Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Teacher {
    private int teacherId;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    public Teacher(int teacherId, String lastName, String firstName, String email, String phoneNumber) {
        this.teacherId = teacherId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
