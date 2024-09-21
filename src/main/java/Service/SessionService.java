package Service;

import Models.Course;
import Models.Session;
import Repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public void createNewSession(Session newSession){
        sessionRepository.create(newSession);
    }
    public List<Session> getAllSessions(){
        return sessionRepository.showAll();
    }
    public Session updateSessionById(int sessionId, Session sessionUpdate) {
        return sessionRepository.update(sessionId,sessionUpdate);
    }
    public void deleteSessionById(int sessionId) {
        sessionRepository.delete(sessionId);
    }
    public Session readSessionById(int sessionId) {
        return sessionRepository.read(sessionId);
    }
    public List<Session> getSessionByCourseId(int courseId){
        return sessionRepository.sessionByCourseId(courseId);
    }

}
