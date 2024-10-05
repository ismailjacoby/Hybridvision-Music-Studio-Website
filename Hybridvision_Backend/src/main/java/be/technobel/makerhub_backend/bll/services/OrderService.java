package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> findOrdersByUsername(String username);
}
