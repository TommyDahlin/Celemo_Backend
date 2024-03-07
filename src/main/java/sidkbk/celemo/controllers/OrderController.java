package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.order.DeleteOrderDTO;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.dto.order.OrderFoundByIdDTO;
import sidkbk.celemo.dto.user.FindUserIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.services.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

// USER
//////////////////////////////////////////////////////////////////////////////////////

// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-one")
    public ResponseEntity<?> getOneOrder(@Valid @RequestBody OrderFoundByIdDTO orderFoundByIdDTO) {
        try {
            return ResponseEntity.ok(orderService.getOneOrder(orderFoundByIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

// SYSTEM
//////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreationDTO orderCreationDTO) {
        Order newOrder = orderService.createOrder(orderCreationDTO);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }


/////////////// REMOVE LATER ///////////////////////
    @DeleteMapping("/dev/delete")
    public ResponseEntity<?> deleteOrder(@Valid @RequestBody DeleteOrderDTO deleteOrderDTO) {
            return orderService.deleteOrder(deleteOrderDTO);
    }


    @GetMapping("/find/user-orders")
    public ResponseEntity<?>findAllOrderForOneUser(@Valid @RequestBody FindUserIdDTO findUserIdDTO){
        List<Order> foundOrder = orderService.findAllOrderForOneUser(findUserIdDTO);
        if (foundOrder.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no order found");
        }else {
            return ResponseEntity.ok().body(foundOrder);
        }
    }
}

