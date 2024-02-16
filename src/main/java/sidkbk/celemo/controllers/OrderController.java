package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.services.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/find")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }


}

