package Repository;

import Models.Student;

import java.util.List;

public interface StudentDAO {

    void create(Student newStudent);
    List<Student> showAll();
    Student updateStudent(int id, Student studentUpdate);
    void deleteStudent (int id);
}
