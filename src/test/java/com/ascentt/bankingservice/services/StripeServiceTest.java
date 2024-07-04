package com.ascentt.bankingservice.services;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.ascentt.bankingservice.services.StripeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StripeServiceTest {

    private StripeService stripeService;

    @BeforeEach
    public void setUp() {
        stripeService = Mockito.spy(new StripeService());
    }

    @Test
    public void testCreateCheckoutSession() throws StripeException {
        // Mock the response for createCheckoutSession
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("id", "cs_test_123");

        doReturn(mockResponse).when(stripeService).createCheckoutSession(anyString());

        Map<String, String> result = stripeService.createCheckoutSession("price_123");

        assertNotNull(result);
        assertEquals("cs_test_123", result.get("id"));
        verify(stripeService, times(1)).createCheckoutSession("price_123");
    }

    @Test
    public void testRetrieveSession() throws StripeException {
        Session sessionMock = mock(Session.class);

        doReturn(sessionMock).when(stripeService).retrieveSession(anyString());

        Session result = stripeService.retrieveSession("session_123");

        assertNotNull(result);
        verify(stripeService, times(1)).retrieveSession("session_123");
    }
}
