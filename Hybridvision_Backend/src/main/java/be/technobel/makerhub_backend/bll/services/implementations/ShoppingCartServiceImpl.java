package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.services.PricingService;
import be.technobel.makerhub_backend.bll.services.ShoppingCartService;
import be.technobel.makerhub_backend.dal.models.entities.*;
import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import be.technobel.makerhub_backend.dal.repositories.CartItemRepository;
import be.technobel.makerhub_backend.dal.repositories.ProductionRepository;
import be.technobel.makerhub_backend.dal.repositories.SamplePackRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import be.technobel.makerhub_backend.pl.models.dtos.CartItemsDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserRepository userRepository;
    private final ProductionRepository productionRepository;
    private final SamplePackRepository samplePackRepository;
    private final CartItemRepository cartItemRepository;
    private final PricingService pricingService;

    public ShoppingCartServiceImpl(
            UserRepository userRepository,
            ProductionRepository productionRepository,
            SamplePackRepository samplePackRepository,
            CartItemRepository cartItemRepository,
            PricingService pricingService) {
        this.userRepository = userRepository;
        this.productionRepository = productionRepository;
        this.samplePackRepository = samplePackRepository;
        this.cartItemRepository = cartItemRepository;
        this.pricingService = pricingService;
    }

    @Override
    public void addToCart(String username, Long itemId, boolean isProduction, LicenseType licenseType) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ShoppingCartEntity shoppingCart = user.getShoppingCart();
        if (shoppingCart == null) {
            throw new IllegalArgumentException("Shopping cart not found for user");
        }

        double price = 0.0;
        CartItemEntity cartItem = new CartItemEntity();

        if (isProduction) {
            ProductionEntity production = productionRepository.findById(itemId)
                    .orElseThrow(() -> new IllegalArgumentException("Production not found"));
            price = pricingService.getPriceForLicense(licenseType);
            cartItem.setProduction(production);
        } else {
            SamplePackEntity samplePack = samplePackRepository.findById(itemId)
                    .orElseThrow(() -> new IllegalArgumentException("Sample pack not found"));
            price = samplePack.getPrice();
            cartItem.setSamplePack(samplePack);
        }

        cartItem.setPrice(price);
        cartItem.setLicenseType(licenseType);
        cartItem.setShoppingCart(shoppingCart);

        cartItemRepository.save(cartItem);

    }

    @Override
    @Transactional
    public void removeFromCart(String username, Long cartItemId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found with ID: " + cartItemId));


        if (!cartItem.getShoppingCart().getUser().equals(user)) {
            throw new IllegalArgumentException("The cart item does not belong to the user's shopping cart");
        }

        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public List<CartItemsDto> getAllCartItems(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getShoppingCart().getItems().stream()
                .map(item -> {
                    CartItemsDto dto = new CartItemsDto();
                    dto.setId(item.getId());
                    dto.setPrice(item.getPrice());
                    if (item.getProduction() != null) {
                        dto.setTitle(item.getProduction().getTitle());
                        dto.setCoverImage(item.getProduction().getCoverImage());
                        dto.setItemType("Production");
                        dto.setLicenseType(item.getLicenseType().toString());
                        dto.setStripePriceId(item.getProduction().getStripePriceId());
                    } else if (item.getSamplePack() != null) {
                        dto.setTitle(item.getSamplePack().getTitle());
                        dto.setCoverImage(item.getSamplePack().getCoverImageUrl());
                        dto.setStripePriceId(item.getSamplePack().getStripePriceId());
                        dto.setItemType("Sample Pack");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
