package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.order.PreviousPurchaseFromOrderDTO;
import sidkbk.celemo.dto.user.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.services.AuctionService;
import sidkbk.celemo.services.OrderService;
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
    @Autowired
    private OrderService orderService;


    // find all/get all accounts
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllUsers(){
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //get average grade, find user by id, filter out what you want to get from a user
    @GetMapping("findfilter")
    public ResponseEntity<?> getUserFilter(@Valid @RequestBody FindUserIdandFilterDTO findUserIdandFilterDTO){
        try {
            return ResponseEntity.ok(userService.getUserFilter(findUserIdandFilterDTO));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find/get using id
    @GetMapping ("/find")
    public ResponseEntity<User> getUserById(@Valid @RequestBody FindUserIdDTO findUserIdDTO){
        Optional<User> user = userService.getUserById(findUserIdDTO);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lists all active listings by UserID
    @GetMapping("/find/activeauction")
    public ResponseEntity<?> getActiveAuction(@Valid @RequestBody FindUserIdDTO findUserIdDTO){
        try {
            return ResponseEntity.ok(auctionService.getActiveAuction(findUserIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    
   // List of all previousPurchases by User
    @GetMapping("/previouspurchase")
    public ResponseEntity<?> getPreviousPurchase(@RequestBody PreviousPurchaseFromOrderDTO previousPurchaseFromOrderDTO) {
        try{
            return ResponseEntity.ok(orderService.findPreviousPurchase(previousPurchaseFromOrderDTO));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

  // put/update // using responseEntity<?> creates a generic wildcard that can return any type of body
    @GetMapping("/find/finishedauction")
    public ResponseEntity<?> getFinishedAuction(@Valid @RequestBody FindUserIdDTO findUserIdDTO){
        try {
            return ResponseEntity.ok(auctionService.getFinishedAuctions(findUserIdDTO));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        }
    @PutMapping("/put")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO){
        try{
            User updatedUser = userService.updateUser(updateUserDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // delete account
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserDTO deleteUserDTO){
        try{
            return userService.deleteUser(deleteUserDTO);

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping ("/favourites/all")
    public ResponseEntity<?> getUserFavouritesById(@Valid @RequestBody FindUserFavouritesDTO favouritesDTO){
        return userService.getUserFavouritesById(favouritesDTO);
    }
    @PutMapping ("/favourite/add")
    public ResponseEntity<?> setUserFavouritesById(@Valid @RequestBody ModifyUserFavouritesDTO addUserFavouritesDTO){
        return userService.setUserFavouritesById(addUserFavouritesDTO);
    }
    @DeleteMapping ("/favourite/delete")
    public ResponseEntity<?> deleteUserFavouritesById(@Valid @RequestBody ModifyUserFavouritesDTO deleteUserFavouritesDTO){
        return userService.deleteUserFavouritesById(deleteUserFavouritesDTO);
    }




}
