package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.controllers.dto.TransactionsCreationDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.services.TransactionsService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    // POST create a transaction
    @PostMapping("/post")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody TransactionsCreationDTO transactionsCreationDTO) {
        return transactionsService.addTransaction(transactionsCreationDTO);
    }

    // GET list of specific users all transactions
    @GetMapping("/find/{user-id}")
    public ResponseEntity<?> findTransactions(@PathVariable("user-id") String userId) {
        return transactionsService.findTransactions(userId);
    }

    // GET all transactions
    @GetMapping("/find")
    public ResponseEntity<?> findAllTransactions() {
        return ResponseEntity.ok(transactionsService.findAllTransactions());
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
