package Controller;

import Models.Student;
import Models.Teacher;
import Service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher")
    public List<Teacher> getAllTeachers(){
        return teacherService.getAllTeachers();
    }
    @DeleteMapping("/teacher/{teacherId}")
    public void deleteTeacher(@PathVariable int teacherId){
        teacherService.deleteTeacher(teacherId);
    }
    @PutMapping("/teacher/{teacherId}")
    public Teacher updateTeacher(@PathVariable (required = true) int  teacherId , @RequestBody Teacher updatedTeacher){
        return teacherService.updateTeacher(teacherId,updatedTeacher);
    }
    @PostMapping("/newTeacher")
    public void createTeacher(@RequestBody Teacher newTeacher){
        teacherService.createTeacher(newTeacher);
    }
    @GetMapping("/teacher/{teacherId}")
    public Teacher getTeacherById(@PathVariable (required = true) int teacherId){
        return teacherService.getTeacherById(teacherId);
    }
}
