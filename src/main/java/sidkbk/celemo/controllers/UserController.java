package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.user.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.User;
import sidkbk.celemo.services.AuctionService;
import sidkbk.celemo.services.UserService;

import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuctionService auctionService;



// USER
//////////////////////////////////////////////////////////////////////////////////////

    // Lists all active listings by UserID
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/find/activeauction/{userId}")
    public ResponseEntity<?> getActiveAuction(@PathVariable("userId") String userId){
        try {
            return ResponseEntity.ok(auctionService.getActiveAuction(userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


    // find finished auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/find/finishedauction/{userId}")
    public ResponseEntity<?> getFinishedAuction(@PathVariable("userId") String userId){
        try {
            return ResponseEntity.ok(auctionService.getFinishedAuctions(userId));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO){
        try{
            User updatedUser = userService.updateUser(updateUserDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping ("/favourites/all/{userId}")
    public ResponseEntity<?> getUserFavouritesById(@PathVariable("userId") String userId){
        return userService.getUserFavouritesById(userId);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping ("/favourite/add")
    public ResponseEntity<?> setUserFavouritesById(@Valid @RequestBody ModifyUserFavouritesDTO addUserFavouritesDTO){
        return userService.setUserFavouritesById(addUserFavouritesDTO);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping ("/favourite/delete")
    public ResponseEntity<?> deleteUserFavouritesById(@Valid @RequestBody ModifyUserFavouritesDTO deleteUserFavouritesDTO){
        return userService.deleteUserFavouritesById(deleteUserFavouritesDTO);
    }
    // find/get using id
    // Changed from GET to POST
    @CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping ("/find-one/{userId}")
    public ResponseEntity<User> getUserById(
            @PathVariable("userId") String userId
        ){
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    // find all/get all accounts
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllUsers(){
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find all/get all accounts
    @GetMapping("/find/all/username-email")
    public ResponseEntity<?> getAllUsernameAndEmail(){
        try {
            return ResponseEntity.ok(userService.getAllUsernameAndEmail());
        } catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users");
        }
    }

    //get average grade, find user by id, filter out what you want to get from a user
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/findfilter/{userId}/{filter}")
    public ResponseEntity<?> getUserFilter(
            @PathVariable("userId") String userId, @PathVariable("filter") String filter
    ){
        try {
            return ResponseEntity.ok(userService.getUserFilter(userId, filter));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // delete account
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserDTO deleteUserDTO){
        try{
            return userService.deleteUser(deleteUserDTO);

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }




    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/dev/update-users-favourite-list/{auctionId}")
    public ResponseEntity<?> updateUsersFavouritesList(@PathVariable("auctionId") String auctionId) {
        userService.removeFavouriteAuctionFromUsers(auctionId);
        return ResponseEntity.ok("Method have removed if there was anything to remove");
    }


}
