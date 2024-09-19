package Controller;

import Models.Course;
import Service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping("/course/teacher")
    public List<Course> getCourseWithTeacher(){
        return courseService.getCourseWithTeacher();
    }
    @GetMapping("/course/teacher/{teacherId}")
    public List<Course> getCourseWithTeacherById(@PathVariable int teacherId){
        return  courseService.getCourseWithTeacherById(teacherId);
    }
}
