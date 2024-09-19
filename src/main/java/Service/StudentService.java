package Service;

import Models.Student;
import Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createNewStudent(Student newStudent){
        studentRepository.create(newStudent);
    }
    public List<Student> showAllStudent(){
        return studentRepository.showAll();
    }
    public Student updateStudent(int id, Student studentUpdate){
        return studentRepository.update(id, studentUpdate);
    }
    public void deleteStudent(int idStudent){
        studentRepository.delete(idStudent);
    }
    public Student readOne(int idStudent){
        return studentRepository.read(idStudent);
    }

    }

