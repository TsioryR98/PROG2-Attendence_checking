package Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class Session {
    private int sessionId;
    private LocalDateTime sessionDate;
    private Course course;

    public Session(int sessionId, LocalDateTime sessionDate, Course course) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.course = course;
    }
}
