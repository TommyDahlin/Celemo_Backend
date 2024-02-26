package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Transactions;
import sidkbk.celemo.services.TransactionsService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    // POST create a transaction
    @PostMapping("/post/{user-id}")
    public ResponseEntity<?> addTransaction(@PathVariable("user-id") String userId,
                                            @Valid @RequestBody Transactions transaction) {
        return transactionsService.addTransaction(userId, transaction);
    }

    // DELETE a transaction
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(transactionsService.deleteTransaction(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
