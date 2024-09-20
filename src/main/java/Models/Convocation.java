package Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Convocation {
    private int convocationId;
    private Teacher teacher;
    private Student student;
    private LocalDateTime convocationDate;
    private String reason;

    public Convocation(int convocationId, Teacher teacher, Student student, LocalDateTime convocationDate, String reason) {
        this.convocationId = convocationId;
        this.teacher = teacher;
        this.student = student;
        this.convocationDate = convocationDate;
        this.reason = reason;
    }
}
