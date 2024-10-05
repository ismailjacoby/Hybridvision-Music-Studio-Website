package be.technobel.makerhub_backend.dal.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "sample_pack")
public class SamplePackEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private String coverImageUrl;
    private String audioUrl;
    private String stripePriceId;

    @ManyToMany(mappedBy = "samplePacks")
    private List<WishlistEntity> wishlists;

    @OneToMany(mappedBy = "samplePack", cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems;


}
