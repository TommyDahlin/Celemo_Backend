package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Transactions;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.TransactionsRepository;
import sidkbk.celemo.repositories.UserRepository;

@Service
public class TransactionsService {

    @Autowired
    TransactionsRepository transactionsRepo;

    @Autowired
    UserRepository userRepository;

    // Add transaction
    public ResponseEntity<?> addTransaction(String userId, Transactions transaction) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Couldn't find user."));
        transaction.setUser(findUser);
        return ResponseEntity.ok(transactionsRepo.save(transaction));
    }

    // Delete a transaction
    public Object deleteTransaction(String id) {
        transactionsRepo.deleteById(id);
        return "Transaction deleted!";
    }
}
