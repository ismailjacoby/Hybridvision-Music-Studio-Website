package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.pl.models.dtos.CheckoutItemDto;
import be.technobel.makerhub_backend.pl.models.dtos.CheckoutSessionRequest;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class StripeController {

    @PostMapping("/payment")
    public ResponseEntity<?> createCheckoutSession(@RequestBody CheckoutSessionRequest request) {
        Stripe.apiKey = "sk_test_51Iz1idIOjZy9IHl8qzMieN3gG15S9ogy29V82e9sYYoKTGvMdKUfbQLNBg3cveEYqxMyLj3wkB9BueTdRRwb41x300qE09n9hP";

        List<SessionCreateParams.LineItem> lineItems = request.getItems().stream()
                .map(item -> SessionCreateParams.LineItem.builder()
                        .setPrice(item.getStripePriceId())
                        .setQuantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        System.out.println("Line items for Stripe Session: " + new Gson().toJson(lineItems));

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl(request.getSuccessUrl())
                        .setCancelUrl(request.getCancelUrl())
                        .addAllLineItem(lineItems)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build();

        try {
            Session session = Session.create(params);

            return ResponseEntity.ok(Map.of("id", session.getId()));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
