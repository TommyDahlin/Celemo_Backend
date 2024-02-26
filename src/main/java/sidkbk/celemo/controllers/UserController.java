package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.User;
import sidkbk.celemo.services.AuctionService;
import sidkbk.celemo.services.OrderService;
import sidkbk.celemo.services.UserService;

import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private OrderService orderService;

    // post/add account/user
    @PostMapping("/post")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user){

        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find all/get all accounts
    @GetMapping("/find")
    public ResponseEntity<?> getAllUsers(){
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //get average grade, find user by id
    @GetMapping("/{id}/{filter}")
    public ResponseEntity<?> getUserFilter(@PathVariable("id") String id,@PathVariable("filter")String filter){
        try {
            return ResponseEntity.ok(userService.getUserFilter(id, filter));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find/get using id
    @GetMapping ("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lists all active listings by User
    @GetMapping("/find/{id}/activeauction")
    public ResponseEntity<?> getActiveAuction(@PathVariable String id){
        try {
            return ResponseEntity.ok(auctionService.getActiveAuction(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    

    // List of all previousPurchases by User
    @GetMapping("/find/{id}/previouspurchase")
    public ResponseEntity<?> getPreviousPurchase(@PathVariable String id) {

        try{
            return ResponseEntity.ok(orderService.findPreviousPurchase(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }





  // put/update // using responseEntity<?> creates a generic wildcard that can return any type of body
    @GetMapping("/find/{id}/finishedauction")
    public ResponseEntity<?> getFinishedAuction(@PathVariable String id){
        try {
            return ResponseEntity.ok(auctionService.getFinishedAuctions(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        }
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User userDetails){
        try{
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // delete account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        try{
            return ResponseEntity.ok(userService.deleteUser(id));

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
