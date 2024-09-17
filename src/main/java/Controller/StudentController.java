package Controller;

import Models.Student;
import Service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/student")
    public List<Student> showAllStudent(){
        return studentService.showAllStudent();
    }
    @DeleteMapping("/student/{studentId}")
    public void deleteStudent(@PathVariable int studentId){
        studentService.deleteStudent(studentId);
    }
    @PutMapping("/student/{studentId}")
    public Student updateStudent(@PathVariable (required = true) int  studentId , @RequestBody Student studentUpdate){
        return studentService.updateStudent(studentId,studentUpdate);
    }
    @PostMapping("/newStudent")
    public void createNewStudent(@RequestBody Student newStudent){
        studentService.createNewStudent(newStudent);
    }
    @GetMapping("/student/{studentId}")
    public Student readOne(@PathVariable (required = true) int studentId){
        return studentService.readOne(studentId);
    }
}
