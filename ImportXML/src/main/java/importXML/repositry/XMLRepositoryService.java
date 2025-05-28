package importXML.repositry;

import importXML.model.GoodGuysModel;
import java.util.List;

public interface XMLRepositoryService {
    public void saveAllGoodGuys(List<GoodGuysModel> goodGuysList);
}
