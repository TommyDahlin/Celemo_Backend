package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Account;
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
    public ResponseEntity<Account> addAccount(@Valid @RequestBody Account account){
        Account newAccount = accountService.addAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    // find all/get all accounts
    @GetMapping("/find")
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    // find/get using id
    @GetMapping ("/find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id){
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // put/update // using responseEntity<?> creates a generic wildcard that can return any type of body
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id, @Valid @RequestBody Account accountDetails){
        try{
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return ResponseEntity.ok(updatedAccount);
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
