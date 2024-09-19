package Service;

import Models.Course;
import Repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public List<Course> getCourseWithTeacher(){
        return courseRepository.courseWithTeacher();
    }
    public List<Course> getCourseWithTeacherById(int teacherId){
        return courseRepository.courseWithTeacherById(teacherId);
    }
}
