package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.controllers.StripeController;
import com.ascentt.bankingservice.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.checkout.Session.CustomerDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StripeController.class)
public class StripeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StripeService stripeService;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testCreateCheckoutSession() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("id", "cs_test_123");

        doReturn(response).when(stripeService).createCheckoutSession(anyString());

        String requestBody = "{ \"priceId\": \"price_1Hh1YGL2Qw4ZZkzznNnCbxMO\" }";

        mockMvc.perform(post("/api/stripe/create-checkout-session")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cs_test_123"));
    }

    @Test
    public void testGetSessionStatus() throws Exception {
        // Mock the Session object and its methods
        Session session = mock(Session.class);
        CustomerDetails customerDetails = mock(CustomerDetails.class);

        doReturn(session).when(stripeService).retrieveSession(anyString());
        doReturn("complete").when(session).getStatus();
        doReturn(customerDetails).when(session).getCustomerDetails();
        doReturn("test@example.com").when(customerDetails).getEmail();

        mockMvc.perform(get("/api/stripe/session-status")
                        .param("sessionId", "cs_test_123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("complete"))
                .andExpect(jsonPath("$.customerEmail").value("test@example.com"));
    }
}
