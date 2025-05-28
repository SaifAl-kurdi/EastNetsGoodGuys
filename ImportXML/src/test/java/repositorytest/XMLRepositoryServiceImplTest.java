package repositorytest;

import importXML.model.GoodGuysModel;
import importXML.repositry.XMLRepositoryServiceImpl;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import java.util.Arrays;
import static org.mockito.Mockito.*;

class XMLRepositoryServiceImplTest {

    @Test
    void testSaveAllGoodGuys() {
        EntityManager entityManagerMock = mock(EntityManager.class);

        XMLRepositoryServiceImpl service = new XMLRepositoryServiceImpl();
        service.setEntityManager(entityManagerMock);

        GoodGuysModel goodGuy1 = new GoodGuysModel();
        GoodGuysModel goodGuy2 = new GoodGuysModel();

        service.saveAllGoodGuys(Arrays.asList(goodGuy1, goodGuy2));

        verify(entityManagerMock, times(2)).persist(any(GoodGuysModel.class));
    }
}

