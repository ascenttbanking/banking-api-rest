package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.repository.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StripeService {

    @Autowired
    private UserRepository userRepository;

    @Value("${stripe.domain}")
    private String domain;

    public Map<String, String> createCheckoutSession(String priceId, Long userId) throws StripeException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(domain + "/success")
                        .setCancelUrl(domain + "/cancel")
                        .setCustomerEmail(user.getEmail())
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice(priceId)
                                        .build())
                        .build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return responseData;
    }

    public Session retrieveSession(String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }
}
