package be.technobel.makerhub_backend.dal.models.entities;

import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "cart_item")
public class CartItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "production_id")
    private ProductionEntity production;

    @ManyToOne
    @JoinColumn(name = "sample_pack_id")
    private SamplePackEntity samplePack;

    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCartEntity shoppingCart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;




}
