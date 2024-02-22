package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.EGender;
import sidkbk.celemo.models.ERole;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;






    // create/add/post user account
    public User addUser(User user){
        if (user.getGender() == null){
            throw new RuntimeException("ERROR: no gender");
        } else if (user.getGender().equals("MALE")){
            user.setGender(EGender.MALE);
        } else if (user.getGender().equals("FEMALE")){
            user.setGender(EGender.FEMALE);
        }
        if (user.getRole() == null){
            user.setRole(ERole.USER);
        } else if (user.getRole().equals("ADMIN")){
            user.setRole(ERole.ADMIN);
        }else if (user.getRole().equals("USER")){
            user.setRole(ERole.USER);
        }
        return userRepository.save(user);
    }

    // get/find all user accounts
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //find user id (average grade)
    public Optional<User> getUserAverageGrade(String id){
        

        return userRepository.findById(id);
    }

    // get/find user account using id
    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }

    // PUT/update user account. checks that new value isn't empty before adding. If something is empty then it will throw EntityNotFoundException
    public User updateUser(String id, User updatedUser){
        return userRepository.findById(id)
        .map(existingUser -> {
            if(updatedUser.getUsername()!=null){
                existingUser.setUsername(updatedUser.getUsername());
            }if(updatedUser.getPassword()!=null){
                existingUser.setPassword(updatedUser.getPassword());
            }if(updatedUser.getDateOfBirth()!=null){
                existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            }if(updatedUser.getEmail()!=null){
                existingUser.setEmail(updatedUser.getEmail());
            }if(updatedUser.getFirstName()!=null){
                existingUser.setFirstName(updatedUser.getFirstName());
            }if(updatedUser.getLastName()!=null){
                existingUser.setLastName(updatedUser.getLastName());
            }if(updatedUser.getAdress_street()!=null){
                existingUser.setAdress_street(updatedUser.getAdress_street());
            }if(updatedUser.getAdress_city()!=null){
                existingUser.setAdress_city(updatedUser.getAdress_city());
            }if(updatedUser.getAdress_postalCode()!=null){
                existingUser.setAdress_postalCode(updatedUser.getAdress_postalCode());
            }if(updatedUser.getBalance()!=0.0){
                existingUser.setBalance(updatedUser.getBalance());
            }if(updatedUser.getPhoto()!=null){
                existingUser.setPhoto(updatedUser.getPhoto());
            }
            return userRepository.save(existingUser);
        })
                .orElseThrow(() -> new EntityNotFoundException("User with id:" + id + " was not found!"));
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


    // delete user account
    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
