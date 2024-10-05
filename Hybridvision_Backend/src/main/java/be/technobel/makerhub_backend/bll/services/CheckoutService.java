package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import be.technobel.makerhub_backend.dal.models.entities.UserEntity;

import java.util.List;

public interface CheckoutService {
    OrderEntity processCheckout(String username);
}
