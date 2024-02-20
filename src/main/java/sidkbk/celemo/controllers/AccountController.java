package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.User;
import sidkbk.celemo.services.AccountService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // post/add account
    @PostMapping("/post")
    public ResponseEntity<User> addAccount(@Valid @RequestBody User user){
        User newUser = accountService.addAccount(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // find all/get all accounts
    @GetMapping("/find")
    public List<User> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    // find/get using id
    @GetMapping ("/find/{id}")
    public ResponseEntity<User> getAccountById(@PathVariable String id){
        Optional<User> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // put/update // using responseEntity<?> creates a generic wildcard that can return any type of body
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id, @Valid @RequestBody User userDetails){
        try{
            User updatedUser = accountService.updateAccount(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // delete account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Recipe with id: " + id + " has been deleted!");
    }
}
