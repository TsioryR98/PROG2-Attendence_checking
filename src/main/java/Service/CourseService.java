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
    public void createNewCourse(Course newCourse){
        courseRepository.create(newCourse);
    }
    public List<Course> showAllCourse(){
        return courseRepository.showAll();
    }
    public Course updateCourse(int courseId, Course courseUpdate){
        return courseRepository.update(courseId,courseUpdate);
    }
    public void deleteCourse(int courseId){
         courseRepository.delete(courseId);
    }
    public Course readCourse(int courseId){
        return courseRepository.read(courseId);
    }
}
