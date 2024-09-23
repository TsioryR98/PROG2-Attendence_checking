package Service;

import Models.Attendance;
import Repository.AttendenceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendenceRepository attendenceRepository;

    public AttendanceService(AttendenceRepository attendenceRepository) {
        this.attendenceRepository = attendenceRepository;
    }
    public List<Attendance> getAttendanceBySessionId(int sessionId){
        return  attendenceRepository.attendancesBySesId(sessionId);
    }
    public void createNewAttendence(Attendance newAttendance){
        attendenceRepository.create(newAttendance);
    }
    public List<Attendance> showAllAttendence(){
        return attendenceRepository.showAll();
    }
    public Attendance updateAttendence(int attendanceId, Attendance attendanceUpdate){
        return attendenceRepository.update(attendanceId,attendanceUpdate);
    }
    public void deleteAttendence(int attendanceId){
        attendenceRepository.delete(attendanceId);
    }
    public Attendance readAttendence(int attendanceId){
        return attendenceRepository.read(attendanceId);
    }
    public List<Attendance> getAttendanceByStudentId(int studentId){
        return attendenceRepository.getAttendanceByStudent(studentId);
    }
    public List<Attendance> getAttendanceByStudentCourse(int studentId, int courseId) {
        return attendenceRepository.attendanceByStudentCourse(studentId,courseId);
    }
    public List<Attendance> getAbsenceProof(){
        return attendenceRepository.absenceProof();
    }
    public List<Attendance> getAbsencesByInterval(int studentId,LocalDateTime startDate, LocalDateTime endDate) {
        return attendenceRepository.getAbsencesBetween(studentId,startDate, endDate);
    }
    public List<Attendance> countAbsences(LocalDateTime startDate, LocalDateTime endDate){
        return attendenceRepository.countAbsenceBetweenDate(startDate,endDate);
    }
}
