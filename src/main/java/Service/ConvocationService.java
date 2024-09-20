package Service;

import Models.Convocation;
import Repository.ConvocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvocationService {
    private final ConvocationRepository convocationRepository;

    public ConvocationService(ConvocationRepository convocationRepository) {
        this.convocationRepository = convocationRepository;
    }
    public void createConvocation(Convocation newConvocation){
        convocationRepository.create(newConvocation);
    }
    public List<Convocation> showAllConvocations(){
        return  convocationRepository.showAll();
    }
    public Convocation updateConvocationById(int convocationId, Convocation convocationUpdate) {
        return convocationRepository.update(convocationId,convocationUpdate);
    }
    public void deleteConvocationById(int convocationId) {
        convocationRepository.delete(convocationId);
    }
    public Convocation readConvocationById(int convocationId) {
        return convocationRepository.read(convocationId);
    }

}
