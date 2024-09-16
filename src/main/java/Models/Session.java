package Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Session {
    private int sessionId;
    private LocalDateTime sessionDate;
    private Course idCourseFK;

    public Session(int sessionId, LocalDateTime sessionDate, Course idCourseFK) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.idCourseFK = idCourseFK;
    }
}
