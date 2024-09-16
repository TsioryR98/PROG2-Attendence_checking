package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attendance {
    private int attendenceId;
    private AttendingStatus attendingStatus;
    private JustifiedStatus justifiedStatus;
    private String proof;

    public Attendance(int attendenceId, AttendingStatus attendingStatus, JustifiedStatus justifiedStatus, String proof) {
        this.attendenceId = attendenceId;
        this.attendingStatus = attendingStatus;
        this.justifiedStatus = justifiedStatus;
        this.proof = proof;
    }
}
