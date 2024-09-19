package Service;

import Models.Enrollment;
import Models.Session;
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
    public void createNewEnroll(Enrollment newEnrollment){
        enrollmentRepository.create(newEnrollment);
    }
    public List<Enrollment> getAllEnrollment(){
        return enrollmentRepository.showAll();
    }
    public Enrollment updateEnrollById(int idEnrollment, Enrollment enrollmentUpdate) {
        return enrollmentRepository.update(idEnrollment,enrollmentUpdate);
    }
    public void deleteEnrollById(int idEnrollment) {
        enrollmentRepository.delete(idEnrollment);
    }
    public Enrollment readEnrollById(int idEnrollment) {
        return enrollmentRepository.read(idEnrollment);
    }

}
