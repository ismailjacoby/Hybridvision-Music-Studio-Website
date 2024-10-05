package be.technobel.makerhub_backend.dal.models.entities;

import be.technobel.makerhub_backend.dal.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDate orderDate;

    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}