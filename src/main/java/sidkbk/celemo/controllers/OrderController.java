package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.order.DeleteOrderDTO;
import sidkbk.celemo.dto.order.OrderCreationDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.services.OrderService;

import java.util.List;

/*@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")*/
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

// USER
//////////////////////////////////////////////////////////////////////////////////////


    // List of all previousPurchases by byer username
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/find/user-orders/{byerUsername}")
    public ResponseEntity<?> getPreviousPurchase(@PathVariable("byerUsername") String byerUsername) {
        try{
            return ResponseEntity.ok(orderService.findOrdersByUserId(byerUsername));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/user-orders/admin/{byerUsername}")
    public ResponseEntity<?>findAllOrderForOneUser(@PathVariable("byerUsername") String byerUsername){
        List<Order> foundOrder = orderService.findAllOrderForOneUser(byerUsername);
        if (foundOrder.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no order found");
        }else {
            return ResponseEntity.ok().body(foundOrder);
        }
    }

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
    @GetMapping("/find-one/{orderId}")
    public ResponseEntity<?> getOneOrder(@PathVariable("orderId") String orderId) {
        try {
            return ResponseEntity.ok(orderService.getOneOrder(orderId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

// SYSTEM
//////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/create") // --- Remove this line later
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreationDTO orderCreationDTO) {
        Order newOrder = orderService.createOrder(orderCreationDTO);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }


/////////////// REMOVE LATER ///////////////////////
    @DeleteMapping("/dev/delete")
    public ResponseEntity<?> deleteOrder(@Valid @RequestBody DeleteOrderDTO deleteOrderDTO) {
            return orderService.deleteOrder(deleteOrderDTO);
    }



}

