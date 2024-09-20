package Controller;

import Models.Convocation;
import Service.ConvocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConvocationController {
    private final ConvocationService convocationService;

    public ConvocationController(ConvocationService convocationService) {
        this.convocationService = convocationService;
    }
    @PostMapping("/convocations")
    public void createConvocation(@RequestBody Convocation newConvocation){
        convocationService.createConvocation(newConvocation);
    }
    @GetMapping("/convocations")
    public List<Convocation> showAllConvocations(){
        return  convocationService.showAllConvocations();
    }
    @PutMapping("/convocations/{convocationId}")
    public Convocation updateConvocationById(@PathVariable int convocationId, @RequestBody Convocation convocationUpdate) {
        return convocationService.updateConvocationById(convocationId,convocationUpdate);
    }
    @DeleteMapping("/convocations/{convocationId}")
    public void deleteConvocationById(@PathVariable int convocationId) {
        convocationService.deleteConvocationById(convocationId);
    }
    @GetMapping("/convocations/{convocationId}")
    public Convocation readConvocationById(@PathVariable int convocationId) {
        return convocationService.readConvocationById(convocationId);
    }
}
