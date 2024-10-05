package be.technobel.makerhub_backend.pl.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CheckoutSessionRequest {
    private List<CheckoutItemDto> items;
    private String currency;
    private String successUrl;
    private String cancelUrl;
}
