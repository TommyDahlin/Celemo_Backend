package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    // create/add/post account
    public User addAccount(User user){
        return userRepository.save(user);
    }

    // get/find all accounts
    public List<User> getAllAccounts(){
        return userRepository.findAll();
    }

    // get/find account using id
    public Optional<User> getAccountById(String id){
        return userRepository.findById(id);
    }

    // PUT/update account. checks that new value isn't empty before adding. If something is empty then it will throw EntityNotFoundException
    public User updateAccount (String id, User updatedUser){
        return userRepository.findById(id)
        .map(existingAccount -> {
            if(updatedUser.getUsername()!=null){
                existingAccount.setUsername(updatedUser.getUsername());
            }if(updatedUser.getPassword()!=null){
                existingAccount.setPassword(updatedUser.getPassword());
            }if(updatedUser.getDateOfBirth()!=null){
                existingAccount.setDateOfBirth(updatedUser.getDateOfBirth());
            }if(updatedUser.getEmail()!=null){
                existingAccount.setEmail(updatedUser.getEmail());
            }if(updatedUser.getFirstName()!=null){
                existingAccount.setFirstName(updatedUser.getFirstName());
            }if(updatedUser.getLastName()!=null){
                existingAccount.setLastName(updatedUser.getLastName());
            }if(updatedUser.getAdress_street()!=null){
                existingAccount.setAdress_street(updatedUser.getAdress_street());
            }if(updatedUser.getAdress_city()!=null){
                existingAccount.setAdress_city(updatedUser.getAdress_city());
            }if(updatedUser.getAdress_postalCode()!=null){
                existingAccount.setAdress_postalCode(updatedUser.getAdress_postalCode());
            }if(updatedUser.getBalance()!=0.0){
                existingAccount.setBalance(updatedUser.getBalance());
            }if(updatedUser.getPhoto()!=null){
                existingAccount.setPhoto(updatedUser.getPhoto());
            }
            return userRepository.save(existingAccount);
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
        userRepository.deleteById(id);
    }
}
