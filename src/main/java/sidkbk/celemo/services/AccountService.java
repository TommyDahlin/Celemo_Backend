package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Account;
import sidkbk.celemo.repositories.AccountRepository;

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

    // PUT/update account. checks that new value isn't empty before adding. If something is empty then it will throw EntityNotFoundException
    public Account updateAccount (String id, Account updatedAccount){
        return accountRepository.findById(id)
        .map(existingAccount -> {
            if(updatedAccount.getUsername()!=null){
                existingAccount.setUsername(updatedAccount.getUsername());
            }if(updatedAccount.getPassword()!=null){
                existingAccount.setPassword(updatedAccount.getPassword());
            }if(updatedAccount.getDateOfBirth()!=null){
                existingAccount.setDateOfBirth(updatedAccount.getDateOfBirth());
            }if(updatedAccount.getEmail()!=null){
                existingAccount.setEmail(updatedAccount.getEmail());
            }if(updatedAccount.getFirstName()!=null){
                existingAccount.setFirstName(updatedAccount.getFirstName());
            }if(updatedAccount.getLastName()!=null){
                existingAccount.setLastName(updatedAccount.getLastName());
            }if(updatedAccount.getAdress_street()!=null){
                existingAccount.setAdress_street(updatedAccount.getAdress_street());
            }if(updatedAccount.getAdress_city()!=null){
                existingAccount.setAdress_city(updatedAccount.getAdress_city());
            }if(updatedAccount.getAdress_postalCode()!=null){
                existingAccount.setAdress_postalCode(updatedAccount.getAdress_postalCode());
            }
            return accountRepository.save(existingAccount);
        })
                .orElseThrow(() -> new EntityNotFoundException("Account with id:" + id + " was not found!"));
    }
/*
username
password
dateOfBirth
email
firstName
lastName
adress_street
adress_postalCode
adress_city
 */


    // delete account
    public void deleteAccount(String id){
        accountRepository.deleteById(id);
    }
}
