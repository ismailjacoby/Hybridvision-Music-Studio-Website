package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.bll.services.WishlistService;
import be.technobel.makerhub_backend.pl.models.dtos.CartItemsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public void addToWishlist(@RequestParam String username, @RequestParam(required = false) Long productionId, @RequestParam(required = false) Long samplePackId) {
        wishlistService.addToWishlist(username, productionId, samplePackId);
    }

    @DeleteMapping("/remove")
    public void removeFromWishlist(@RequestParam String username, @RequestParam(required = false) Long productionId, @RequestParam(required = false) Long samplePackId) {
        wishlistService.removeFromWishlist(username, productionId, samplePackId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartItemsDto>> getWishlist(@RequestParam String username){
        try {
            List<CartItemsDto> items = wishlistService.getAllWishlistItemsByUsername(username);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
