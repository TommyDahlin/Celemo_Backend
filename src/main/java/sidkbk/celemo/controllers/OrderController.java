package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
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
    public ResponseEntity<?> updateOrder(@PathVariable("id") String orderId,
                                         @Valid @RequestBody Order updatedOrder) {
        try {
            return ResponseEntity.ok(orderService.updateOrder(orderId, updatedOrder));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }
    // hej hej
}

