package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.User;
import sidkbk.celemo.services.AuctionService;
import sidkbk.celemo.services.UserService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuctionService auctionService;

    // post/add account/user
    @PostMapping("/post")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){

        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // find all/get all accounts
    @GetMapping("/find")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //get average grade, find user by id
    @GetMapping("/averageGrade/{id}")
    public ResponseEntity<User> getUserAverageGrade(@PathVariable String id){
        Optional<User> user = userService.getUserAverageGrade(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // find/get using id
    @GetMapping ("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lists all active listings by User
    @GetMapping("/find/{id}/activeAuction")
    public List<Auction> getActiveAuction(@PathVariable String id){
        return auctionService.getActiveAuction(id);
    }
    // put/update // using responseEntity<?> creates a generic wildcard that can return any type of body
    @GetMapping("/find/{id}/finishedAuction")
    public List<Auction> getFinishedAuction(@PathVariable String id){return auctionService.getFinishedAuctions(id);}
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
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Recipe with id: " + id + " has been deleted!");
    }
}
