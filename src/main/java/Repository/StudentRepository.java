package Repository;

import Models.Student;

import java.util.List;

public class StudentRepository implements StudentDAO{
    @Override
    public void create(Student newStudent) {

    }

    @Override
    public List<Student> showAll() {
        return List.of();
    }

    @Override
    public Student updateStudent(int id, Student studentUpdate) {
        return null;
    }

    @Override
    public void deleteStudent(int id) {

    }
}
