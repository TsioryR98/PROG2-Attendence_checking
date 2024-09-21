package Controller;

import Models.Attendance;
import Service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttendenceController {
    private final AttendanceService attendanceService;
    public AttendenceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    @GetMapping("/attendances/session/{sessionId}")
    public List<Attendance> getAttendanceBySessionId(@PathVariable int sessionId){
        return attendanceService.getAttendanceBySessionId(sessionId);
    }

    @PostMapping("/attendances")
    public void createNewAttendence(@RequestBody Attendance newAttendance){
        attendanceService.createNewAttendence(newAttendance);
    }
    @GetMapping("/attendances")
    public List<Attendance> showAllAttendence(){
        return attendanceService.showAllAttendence();
    }
    @PutMapping("/attendances/{attendanceId}")
    public Attendance updateAttendenceById(@PathVariable int attendanceId, @RequestBody Attendance attendanceUpdate){
        return attendanceService.updateAttendence(attendanceId, attendanceUpdate);
    }
    @DeleteMapping("/attendances/{attendanceId}")
    public void deleteAttendenceById(@PathVariable int attendanceId){
        attendanceService.deleteAttendence(attendanceId);
    }
    @GetMapping("/attendances/{attendanceId}")
    public Attendance readAttendenceById(@PathVariable int attendanceId){
        return attendanceService.readAttendence(attendanceId);
    }
    @GetMapping("/student/{studentId}/attendances")
    public List<Attendance> getAbsenceByStudentId(@PathVariable int studentId) {
        return attendanceService.getAttendanceByStudentId(studentId);
    }
    @GetMapping("/student/{studentId}/courses/{courseId}/attendances")
    public List<Attendance> getAbsenceByStudentCourse(@PathVariable int studentId, @PathVariable int courseId) {
        return attendanceService.getAttendanceByStudentCourse(studentId, courseId);
    }
}
