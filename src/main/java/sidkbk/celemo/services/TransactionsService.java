package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public ResponseEntity<?> addTransaction(String userId, Transactions transaction) {
        // Check if user exist
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Couldn't find user."));
        // Update users balance
        findUser.setBalance( (findUser.getBalance() - transaction.getTransactionAmount()) );
        // Set user REF in transaction body
        transaction.setUser(findUser);
        // Save new transaction to db and return 200 OK with transaction details.
        return ResponseEntity.ok(transactionsRepo.save(transaction));
    }

    // Delete a transaction
    public Object deleteTransaction(String id) {
        transactionsRepo.deleteById(id);
        return "Transaction deleted!";
    }

    public ResponseEntity<?> findTransactions(String userId) {
        // Check if user exists
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
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
