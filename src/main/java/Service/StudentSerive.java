package Service;

import Models.Student;
import Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentSerive {
    private final StudentRepository studentRepository;

    public StudentSerive(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public void createNewStudent(Student newStudent){
        studentRepository.create(newStudent);
    }
    public List<Student> showAllStudent(){
       return studentRepository.showAll();
    }
    public Student updateStudent(int id, Student studentUpdate){
        return studentRepository.updateById(id, studentUpdate);
    }
    public void deleteStudent(int idStudent){
        studentRepository.deleteById(idStudent);
    }
}
