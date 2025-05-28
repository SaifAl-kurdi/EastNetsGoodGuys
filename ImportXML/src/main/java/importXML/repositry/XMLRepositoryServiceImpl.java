package importXML.repositry;

import importXML.model.GoodGuysModel;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class XMLRepositoryServiceImpl implements XMLRepositoryService {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void saveAllGoodGuys(List<GoodGuysModel> goodGuysList) {
        for (int i = 0; i < goodGuysList.size(); i++) {
            entityManager.persist(goodGuysList.get(i));
            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}