package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.DeleteTransactionDTO;
import sidkbk.celemo.dto.FindTransactionsForUserDTO;
import sidkbk.celemo.dto.TransactionsCreationDTO;
import sidkbk.celemo.models.Transactions;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.TransactionsRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    TransactionsRepository transactionsRepo;

    @Autowired
    UserRepository userRepository;

    // Add transaction
    public ResponseEntity<?> addTransaction(TransactionsCreationDTO transactionsCreationDTO) {
        // Check if user exist
        User findUser = userRepository.findById(transactionsCreationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Couldn't find user."));
        // Update users balance
        findUser.setBalance( (findUser.getBalance() - transactionsCreationDTO.getTransactionAmount()) );
        userRepository.save(findUser);

        Transactions newTransaction = new Transactions();

        newTransaction.setUser(findUser); // Set user REF in transaction body
        newTransaction.setTransactionAmount(transactionsCreationDTO.getTransactionAmount()); // Set amount
        // Save new transaction to db and return 200 OK with transaction details.
        return ResponseEntity.ok(transactionsRepo.save(newTransaction));
    }

    // Delete a transaction
    public ResponseEntity<?> deleteTransaction(DeleteTransactionDTO deleteTransactionDTO) {
        transactionsRepo.findById(deleteTransactionDTO.getTransactionId()).orElseThrow(
                () -> new RuntimeException("Transaction does not exist!"));
        transactionsRepo.deleteById(deleteTransactionDTO.getTransactionId());
        return ResponseEntity.ok("Transaction deleted!");
    }

    // List all transactions for a specific user
    public ResponseEntity<?> findTransactions(FindTransactionsForUserDTO findTransactionsForUserDTO) {
        // Check if user exists
        User foundUser = userRepository.findById(findTransactionsForUserDTO.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found"));
        // Temp save all transactions
        List<Transactions> allTransactions = transactionsRepo.findAll();
        List<Transactions> foundTransactions = new ArrayList<>(); // List for found transactions for specified user
        for (Transactions transactions : allTransactions) { // Loop all transactions
            // Temp save transactions if any was found
            if (transactions.getUser().getId().equals(foundUser.getId())) {
                foundTransactions.add(transactions);
            }
        }
        // If no transactions were found for specified user throw error
        if (foundTransactions.isEmpty()) {
            throw new RuntimeException("User does not have any transactions");
        }
        return ResponseEntity.ok(foundTransactions);
    }

    public Object findAllTransactions() {
        return transactionsRepo.findAll();
    }
}
