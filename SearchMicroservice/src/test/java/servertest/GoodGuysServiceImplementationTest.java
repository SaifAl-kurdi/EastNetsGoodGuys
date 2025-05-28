package servertest;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import search.dtos.GoodGuysDTO;
import search.model.GoodGuys;
import search.repository.GoodGuyRepository;
import search.service.GoodGuysServiceImplementation;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GoodGuysServiceImplementationTest {

    @Test
    void testGetFilteredGoodGuys() {
        GoodGuyRepository mockRepository = Mockito.mock(GoodGuyRepository.class);
        GoodGuysServiceImplementation service = new GoodGuysServiceImplementation();
        service.goodGuyRepository = mockRepository;

        List<GoodGuys> mockedEntities = new ArrayList<>();
        GoodGuys goodGuy = new GoodGuys();
        goodGuy.setId(1);
        goodGuy.setAcceptedString("acceptedString1");
        mockedEntities.add(goodGuy);

        when(mockRepository.findGoodGuys(any(), any(), any(), any(), any(), any(),any(), any(), any(), any(), any(), any())).thenReturn(mockedEntities);

        List<GoodGuysDTO> result = service.getFilteredGoodGuys(1, "acceptedString1", "condition", "comments",
                "2025-01-01", "tester", "none","yes", 5, "extID", "accMatch", "entMainName");

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> acceptedStringCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockRepository, times(1)).findGoodGuys(idCaptor.capture(), acceptedStringCaptor.capture(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());

        assertEquals(1, idCaptor.getValue());
        assertEquals("acceptedString1", acceptedStringCaptor.getValue());
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("acceptedString1", result.get(0).getAcceptedString());
    }
}