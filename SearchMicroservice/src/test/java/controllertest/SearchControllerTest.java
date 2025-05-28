package controllertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import search.controller.SearchController;
import search.dtos.GoodGuysDTO;
import search.service.GoodGuysService;
import eastnets.validate.token.TokenValidator;
import org.eclipse.microprofile.context.ManagedExecutor;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchControllerTest {

    @Mock
    private GoodGuysService goodGuysService;

    @Mock
    private TokenValidator tokenValidator;

    @Mock
    private ManagedExecutor managedExecutor;

    @InjectMocks
    private SearchController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller.setManagedExecutor(managedExecutor);
    }

    @Test
    public void testGetGoodGuys() {
        // Mock token validation
        when(tokenValidator.isValidToken("valid-token")).thenReturn(true);

        GoodGuysDTO dto = new GoodGuysDTO();
        dto.setId(1);
        dto.setAcceptedString("Accepted");
        when(goodGuysService.getFilteredGoodGuys(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(dto));

        when(managedExecutor.supplyAsync(any())).thenAnswer(invocation -> {
            return CompletableFuture.completedFuture(
                    goodGuysService.getFilteredGoodGuys(null, null, null, null, null, null, null, null, null, null, null, null)
            );
        });

        List<GoodGuysDTO> result = controller.getGoodGuys("valid-token", null)
                .toCompletableFuture().join();

        assertEquals(1, result.size());
        assertEquals("Accepted", result.get(0).getAcceptedString());
        verify(goodGuysService, times(1)).getFilteredGoodGuys(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
}

