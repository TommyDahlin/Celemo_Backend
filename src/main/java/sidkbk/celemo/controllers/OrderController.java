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

    @GetMapping("/find/{id}")
    public Order getOneOrder(@PathVariable("id") String id ) {
        return orderService.getOneOrder(id);
    }

    @PutMapping("/update/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable("id") String _id) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }
    // hej hej
}

