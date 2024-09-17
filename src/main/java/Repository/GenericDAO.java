package Repository;

import Models.Student;

import java.util.List;

public interface GenericDAO <Model>{
    void create(Student newStudent);
    List<Student> showAll();
    Student update(int id, Student studentUpdate);
    void delete(int id);
    Student read(int studentId);
}
