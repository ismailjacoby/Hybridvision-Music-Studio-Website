package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.services.OrderService;
import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import be.technobel.makerhub_backend.dal.repositories.OrderRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderEntity> findOrdersByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> orderRepository.findByUserId(user.getId()))
                .orElseThrow(()-> new NotFoundException("User not found"));
    }


}
