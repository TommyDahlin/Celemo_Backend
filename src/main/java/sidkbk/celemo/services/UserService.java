package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.user.CreateUserDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.EGender;
import sidkbk.celemo.models.ERole;
import sidkbk.celemo.models.Role;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.RoleRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    // create/add/post user account
    public User createUser(CreateUserDTO createUserDTO){
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
        user.setDateOfBirth(createUserDTO.getDateOfBirth());
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setAdress_city(createUserDTO.getAdress_city());
        user.setAdress_street(createUserDTO.getAdress_street());
        user.setAdress_postalCode(createUserDTO.getAdress_postalCode());
        user.setGender(createUserDTO.getGender());
        //checks if  password is longer than 8 chars and contains atleast one upperCase
        user.isPasswordCorrect(user);

        //checks that gender isn't null
        if (user.getGender() == null){
            throw new RuntimeException("ERROR: no gender");
        } else if (user.getGender().equals("MALE")){ //string to enum
            user.setGender(EGender.MALE);
        } else if (user.getGender().equals("FEMALE")){//string to enum
            user.setGender(EGender.FEMALE);
        }
        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = createUserDTO.getUsersRoles();
        if (strRoles.isEmpty()){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: role is not found"));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                case "ADMIN" -> {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(()-> new RuntimeException("Error: User Role couldn't be found"));
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Admin Role couldn't be found"));
                    roles.add(adminRole);
                    roles.add(userRole);
                }
                case "USER" -> {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role couldn't be found"));
                    roles.add(userRole);
                }
                }
            });

        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // get/find all user accounts
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //find user variable with filter. For example : grade
    public String getUserFilter(String id, String filter){ //userId and filter, filter can be grade, username, firstName, lastName
        User user = userRepository.findById(id).get();
        return  user.getFilter(filter);

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
    public String deleteUser(String id){
        userRepository.deleteById(id);
        return "Deleted user successfully!";
    }
}
