package be.technobel.makerhub_backend.pl.models.dtos;

import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import be.technobel.makerhub_backend.dal.models.enums.PaymentStatus;

import java.time.LocalDate;

public record OrderDto(Long id,
                       LocalDate orderDate,
                       double totalAmount,
                       PaymentStatus paymentStatus) {

    public static OrderDto fromEntity(OrderEntity order){
        return new OrderDto(order.getId(),order.getOrderDate(),order.getTotalAmount(),order.getPaymentStatus());
    }
}
