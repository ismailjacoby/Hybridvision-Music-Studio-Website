package be.technobel.makerhub_backend.pl.models.dtos;

import be.technobel.makerhub_backend.dal.models.entities.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CartItemsDto {
    private Long id;
    private String title;
    private String coverImage;
    private double price;
    private String itemType;
    private String licenseType;
    private String stripePriceId;


}
