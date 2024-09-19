package Controller;

import Models.Enrollment;
import Service.EnrollmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
