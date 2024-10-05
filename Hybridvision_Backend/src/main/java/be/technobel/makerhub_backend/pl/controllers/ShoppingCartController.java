package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.bll.services.ShoppingCartService;
import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import be.technobel.makerhub_backend.pl.models.dtos.CartItemsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<String> addItemToCart(
            @RequestParam String username,
            @RequestParam Long itemId,
            @RequestParam boolean isProduction,
            @RequestParam LicenseType licenseType) {
        try {
            if (isProduction && licenseType == null) {
                throw new IllegalArgumentException("License type is required for production items.");
            }
            shoppingCartService.addToCart(username, itemId, isProduction, licenseType);
            return new ResponseEntity<>("Item added to cart successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while adding the item to the cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeItem/{username}/{cartItemId}")
    public ResponseEntity<String> removeItemFromCart(
            @PathVariable String username,
            @PathVariable Long cartItemId) {
        try {
            shoppingCartService.removeFromCart(username, cartItemId);
            return new ResponseEntity<>("Item removed from cart successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while removing the item from the cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemsDto>> getCartItems(String username) {
        try {
            List<CartItemsDto> items = shoppingCartService.getAllCartItems(username);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
