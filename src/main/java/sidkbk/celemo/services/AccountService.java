package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Account;
import sidkbk.celemo.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;


    // create/add/post account
    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    // get/find all accounts
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    // get/find account using id
    public Optional<Account> getAccountById(String id){
        return accountRepository.findById(id);
    }

    // PUT/update account
    public Account updateAccount (String id, Account updatedAccount){
        .map(existingAccount -> {
            if(){

            }
            if(){

            }
            return accountRepository.save(existingAccount);
        })
                .orElseThrow(() -> EntityNotFoundException("Account with id:" + id + " was not found!"));
    }

    // delete account
    public void deleteAccount(String id){
        accountRepository.deleteById(id);
    }
}
