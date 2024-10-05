package be.technobel.makerhub_backend.pl.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckoutItemDto {
    private String name;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private String stripePriceId;
    private long amount;
    private long quantity;
}
