package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import be.technobel.makerhub_backend.pl.models.dtos.CartItemsDto;

import java.util.List;

public interface ShoppingCartService {
    void addToCart(String username, Long itemId, boolean isProduction, LicenseType licenseType);
    void removeFromCart(String username, Long cartItemId);
    List<CartItemsDto> getAllCartItems(String username);
}
