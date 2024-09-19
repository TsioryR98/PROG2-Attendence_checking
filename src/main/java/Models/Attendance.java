package Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Attendance {
    private int attendenceId;
    private AttendingStatus attendingStatus;
    private JustifiedStatus justifiedStatus;
    private String proof;
    private Session session;
    private Student student;

    public Attendance(int attendenceId,
                      AttendingStatus attendingStatus,
                      JustifiedStatus justifiedStatus,
                      String proof,
                      Session session,
                      Student student) {
        this.attendenceId = attendenceId;
        this.attendingStatus = attendingStatus;
        this.justifiedStatus = justifiedStatus;
        this.proof = proof;
        this.session = session;
        this.student = student;
    }
}
