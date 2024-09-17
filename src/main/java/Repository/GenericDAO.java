package Repository;

import Models.Student;

import java.util.List;

public interface GenericDAO <Model>{
    void create(Model newModel);
    List<Model> showAll();
    Model update(int id, Model modelUpdate);
    void delete(int id);
    Model read(int modelId);
}
