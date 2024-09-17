package Service;

import Models.Teacher;
import Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public void createTeacher(Teacher newTeacher) {
        teacherRepository.create(newTeacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.showAll();
    }

    public Teacher getTeacherById(int id) {
        return teacherRepository.read(id);
    }

    public Teacher updateTeacher(int teacherId, Teacher updatedTeacher) {
        return teacherRepository.update(teacherId, updatedTeacher);
    }

    public void deleteTeacher(int id) {
        teacherRepository.delete(id);
    }
}
