package Controller;

import Models.Attendance;
import Service.AttendanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttendenceController {
    private final AttendanceService attendanceService;
    public AttendenceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    @GetMapping("/attendance/session/{sessionId}")
    public List<Attendance> getAttendanceBySessionId(@PathVariable int sessionId){
        return attendanceService.getAttendanceBySessionId(sessionId);
    }

}
