package Controller;

import Models.Course;
import Service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping("/courses/teacher")
    public List<Course> getCourseWithTeacher(){
        return courseService.getCourseWithTeacher();
    }
    @GetMapping("/courses/teacher/{teacherId}")
    public List<Course> getCourseWithTeacherById(@PathVariable int teacherId){
        return  courseService.getCourseWithTeacherById(teacherId);
    }
    @PostMapping("/courses")
    public void createNewCourse(@RequestBody Course newCourse){
        courseService.createNewCourse(newCourse);
    }
    @GetMapping("/courses")
    public List<Course> showAllCourse(){
        return courseService.showAllCourse();
    }
    @PutMapping("/courses/{courseId}")
    public Course updateCourse(@PathVariable int courseId, @RequestBody Course courseUpdate){
        return courseService.updateCourse(courseId,courseUpdate);
    }
    @DeleteMapping("/courses/{courseId}")
    public void deleteCourseById(@PathVariable int courseId){
        courseService.deleteCourse(courseId);
    }
    @GetMapping("/courses/{courseId}")
    public Course readCourseById(@PathVariable int courseId){
        return courseService.readCourse(courseId);
    }

}
