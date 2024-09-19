package Service;

import Models.Attendance;
import Repository.AttendenceRepository;
import org.springframework.stereotype.Service;

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
}
