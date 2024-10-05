package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.services.CheckoutService;
import be.technobel.makerhub_backend.dal.models.entities.CartItemEntity;
import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import be.technobel.makerhub_backend.dal.models.entities.ShoppingCartEntity;
import be.technobel.makerhub_backend.dal.models.entities.UserEntity;
import be.technobel.makerhub_backend.dal.models.enums.PaymentStatus;
import be.technobel.makerhub_backend.dal.repositories.OrderRepository;
import be.technobel.makerhub_backend.dal.repositories.ShoppingCartRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CheckoutServiceImpl(ShoppingCartRepository shoppingCartRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OrderEntity processCheckout(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException( "User not found"));

        ShoppingCartEntity cart = user.getShoppingCart();
        if(cart == null || cart.getItems().isEmpty()){
            throw new IllegalArgumentException("Shopping cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(calculateTotal(cart.getItems()));
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setItems(new ArrayList<>());

        // Transfers CartItemEntities to OrderEntity
        for (CartItemEntity item : cart.getItems()) {
            item.setOrder(order);
            item.setShoppingCart(null);
            order.getItems().add(item);
        }

        cart.getItems().clear();
        shoppingCartRepository.save(cart);

        return orderRepository.save(order);
    }


    private double calculateTotal(List<CartItemEntity> items) {
        return items.stream()
                .mapToDouble(CartItemEntity::getPrice)
                .sum();
    }
}