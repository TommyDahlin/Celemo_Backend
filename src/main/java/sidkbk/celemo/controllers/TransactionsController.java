package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.transactions.DeleteTransactionDTO;
import sidkbk.celemo.dto.transactions.FindTransactionsForUserDTO;
import sidkbk.celemo.dto.transactions.TransactionsCreationDTO;
import sidkbk.celemo.services.TransactionsService;
@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

// USER
//////////////////////////////////////////////////////////////////////////////////////

    // GET list of specific users all transactions
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/find/all-user")
    public ResponseEntity<?> findTransactions(@Valid @RequestBody FindTransactionsForUserDTO findTransactionsForUserDTO) {
        return transactionsService.findTransactions(findTransactionsForUserDTO);
    }

// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    // GET all transactions
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all")
    public ResponseEntity<?> findAllTransactions() {
        return ResponseEntity.ok(transactionsService.findAllTransactions());
    }

// SYSTEM
//////////////////////////////////////////////////////////////////////////////////////

    // POST create a transaction
    @PostMapping("/create")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody TransactionsCreationDTO transactionsCreationDTO) {
        return transactionsService.addTransaction(transactionsCreationDTO);
    }



//////////////// REMOVE LATER /////////////////////////////////////////

    // DELETE a transaction
    @DeleteMapping("/dev/delete")
    public ResponseEntity<?> deleteTransaction(@Valid @RequestBody DeleteTransactionDTO deleteTransactionDTO) {
        return transactionsService.deleteTransaction(deleteTransactionDTO);
    }
}
