package Repository;

import Models.Student;

import java.util.List;

public interface StudentDAO {

    void create(Student newStudent);
    List<Student> showAll();
    Student updateById(int id, Student studentUpdate);
    void deleteById(int id);
}
