package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.bll.services.CheckoutService;
import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import be.technobel.makerhub_backend.pl.models.dtos.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> processCheckout(@RequestParam String username){
        try{
            OrderEntity order = checkoutService.processCheckout(username);
            OrderDto orderDto = OrderDto.fromEntity(order);
            return ResponseEntity.ok(orderDto);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
