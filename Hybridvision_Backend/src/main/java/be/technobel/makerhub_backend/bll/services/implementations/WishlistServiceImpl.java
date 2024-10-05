package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.services.WishlistService;
import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import be.technobel.makerhub_backend.dal.models.entities.SamplePackEntity;
import be.technobel.makerhub_backend.dal.models.entities.UserEntity;
import be.technobel.makerhub_backend.dal.models.entities.WishlistEntity;
import be.technobel.makerhub_backend.dal.repositories.ProductionRepository;
import be.technobel.makerhub_backend.dal.repositories.SamplePackRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import be.technobel.makerhub_backend.dal.repositories.WishlistRepository;
import be.technobel.makerhub_backend.pl.models.dtos.CartItemsDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final SamplePackRepository samplePackRepository;
    private final ProductionRepository productionRepository;
    private final UserRepository userRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, SamplePackRepository samplePackRepository, ProductionRepository productionRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.samplePackRepository = samplePackRepository;
        this.productionRepository = productionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addToWishlist(String username, Long productionId, Long samplePackId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        WishlistEntity wishlist = user.getWishlist();

        if (wishlist == null) {
            wishlist = new WishlistEntity();
            wishlist.setUser(user);
            user.setWishlist(wishlist);
        }

        if (productionId != null) {
            ProductionEntity production = productionRepository.findById(productionId)
                    .orElseThrow(() -> new RuntimeException("Production not found."));
            wishlist.getProductions().add(production);
        } else if (samplePackId != null) {
            SamplePackEntity samplePack = samplePackRepository.findById(samplePackId)
                    .orElseThrow(() -> new RuntimeException("Sample Pack not found."));
            wishlist.getSamplePacks().add(samplePack);
        }

        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(String username, Long productionId, Long samplePackId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        WishlistEntity wishlist = user.getWishlist();

        if (wishlist != null) {
            if (productionId != null) {
                ProductionEntity production = productionRepository.findById(productionId)
                        .orElseThrow(() -> new RuntimeException("Production not found."));
                wishlist.getProductions().remove(production);
            } else if (samplePackId != null) {
                SamplePackEntity samplePack = samplePackRepository.findById(samplePackId)
                        .orElseThrow(() -> new RuntimeException("Sample Pack not found."));
                wishlist.getSamplePacks().remove(samplePack);
            }

            wishlistRepository.save(wishlist);
        }
    }

    @Override
    public List<CartItemsDto> getAllWishlistItemsByUsername(String username) throws NotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found for username: " + username));

        WishlistEntity wishlist = user.getWishlist();

        List<CartItemsDto> itemsDto = new ArrayList<>();

        List<CartItemsDto> productionItems = wishlist.getProductions().stream()
                .map(prod -> new CartItemsDto(prod.getId(), prod.getTitle(), prod.getCoverImage(), 24.95, "Production", null, null))
                .collect(Collectors.toList());

        List<CartItemsDto> samplePackItems = wishlist.getSamplePacks().stream()
                .map(sp -> new CartItemsDto(sp.getId(), sp.getTitle(), sp.getCoverImageUrl(), sp.getPrice(), "SamplePack", null, sp.getStripePriceId()))
                .collect(Collectors.toList());

        itemsDto.addAll(productionItems);
        itemsDto.addAll(samplePackItems);

        return itemsDto;
    }
}
