package Controller;

import Models.Course;
import Models.Enrollment;
import Service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enrollments/course")
    public List<Enrollment> getAllStudentWithCourse(){
        return enrollmentService.getAllStudentWithCourse();
    }
    @GetMapping("/enrollments/course/{courseId}")
    public List<Enrollment> getStudentsWithCourseById(@PathVariable int courseId){
        return enrollmentService.getStudentsWithCourseById(courseId);
    }
    /*  /enrollments/{enrollmentId}:*/
    @PostMapping("/enrollments")
    public void createNewEnroll(@RequestBody Enrollment newEnrollment){
        enrollmentService.createNewEnroll(newEnrollment);
    }
    @GetMapping("/enrollments")
    public List<Enrollment> getAllEnrollment(){
        return enrollmentService.getAllEnrollment();
    }
    @PutMapping("/enrollments/{enrollmentId}")
    public Enrollment updateEnrollById(@PathVariable int enrollmentId, @RequestBody Enrollment enrollmentUpdate){
        return enrollmentService.updateEnrollById(enrollmentId,enrollmentUpdate);
    }
    @DeleteMapping("/enrollments/{enrollmentId}")
    public void deleteEnrollById(@PathVariable int enrollmentId){
        enrollmentService.deleteEnrollById(enrollmentId);
    }
    @GetMapping("/enrollments/{enrollmentId}")
    public Enrollment readEnrollById(@PathVariable int enrollmentId){
        return enrollmentService.readEnrollById(enrollmentId);
    }
}
