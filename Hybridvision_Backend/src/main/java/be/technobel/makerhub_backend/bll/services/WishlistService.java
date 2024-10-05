package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.pl.models.dtos.CartItemsDto;


import java.util.List;


public interface WishlistService {
    void addToWishlist(String username, Long productionId, Long samplePackId);
    void removeFromWishlist(String username, Long productionId, Long samplePackId);
    List<CartItemsDto> getAllWishlistItemsByUsername(String username);
}
