package Service;

import Models.Enrollment;
import Repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment>getAllStudentWithCourse(){
        return enrollmentRepository.studentWithCourse();
    }
    public List<Enrollment> getStudentsWithCourseById(int courseId){
        return enrollmentRepository.studentWithCourseById(courseId);
    }

}
