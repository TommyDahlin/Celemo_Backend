package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.dto.order.OrderFoundByIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.services.OrderService;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;


   @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreationDTO orderCreationDTO) {
       Order newOrder = orderService.createOrder(orderCreationDTO);
       return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/order")
    public ResponseEntity<?> getOneOrder(@RequestBody OrderFoundByIdDTO orderFoundByIdDTO) {
        try {
            return ResponseEntity.ok(orderService.getOneOrder(orderFoundByIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") String orderId,
                                         @Valid @RequestBody Order updatedOrder) {
        try {
            return ResponseEntity.ok(orderService.updateOrder(orderId, updatedOrder));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        try {
            return ResponseEntity.ok(orderService.deleteOrder(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

