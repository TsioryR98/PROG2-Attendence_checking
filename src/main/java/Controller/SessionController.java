package Controller;

import Models.Session;
import Service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SessionController {
    private final SessionService sessionService;
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @PostMapping("/sessions")
    public void createNewSession(@RequestBody Session newSession){
        sessionService.createNewSession(newSession);
    }
    @GetMapping("/sessions")
    public List<Session> getAllSessions(){
        return sessionService.getAllSessions();
    }
    @PutMapping("/sessions/{sessionId}")
    public Session updateSessionById(@PathVariable int sessionId, @RequestBody Session sessionUpdate){
        return sessionService.updateSessionById(sessionId, sessionUpdate);
    }
    @DeleteMapping("/sessions/{sessionId}")
    public void deleteSessionById(@PathVariable int sessionId){
        sessionService.deleteSessionById(sessionId);
    }
    @GetMapping("/sessions/{sessionId}")
    public Session readSessionById(@PathVariable int sessionId){
        return sessionService.readSessionById(sessionId);
    }
}
