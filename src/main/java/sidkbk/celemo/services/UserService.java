package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.user.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.*;


import sidkbk.celemo.repositories.*;

import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.ReviewsRepo;
import sidkbk.celemo.repositories.RoleRepository;
import sidkbk.celemo.repositories.UserRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ReviewsRepo reviewsRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    OrderRepository orderRepository;


    // HELENA:
    // är det här regsiter eller inte?
    // det ser ut som ett ihopkok av createUser och register eftersom ni blandar in roller
    // om det här är en metod för att registrera en user så bör metodsignaturen spegla det
    // den bör inte heller ligga i en UserService utan i så fall => AuthService


    // get/find all user accounts
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //get all username and mail
    public List<String> getAllUsernameAndEmail() {

        try {
            return userRepository.findAll()
                    .stream()
                    .map(User::getUsernameAndEmail)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new NullPointerException("no users found");
        }

    }

    //find user variable with filter. For example : grade
    public String getUserFilter(String userId, String filter) { //userId and filter, filter can be grade, username, firstName, lastName
        User user = userRepository.findById(userId).get();
        return user.getFilter(filter);

    }

    // get/find user account using id
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }


    // PUT/update user account. checks that new value isn't empty before adding. If something is empty then it will throw EntityNotFoundException
    public User updateUser(UpdateUserDTO updateUserDTO) {
        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = updateUserDTO.getUsersRoles();
        return userRepository.findById(updateUserDTO.getUserId())
                .map(existingUser -> {
                    Optional.ofNullable(updateUserDTO.getUsername()).ifPresent(existingUser::setUsername);
                    Optional.ofNullable(updateUserDTO.getPassword()).ifPresent(existingUser::setPassword);
                    Optional.ofNullable(updateUserDTO.getDateOfBirth()).ifPresent(existingUser::setDateOfBirth);
                    Optional.ofNullable(updateUserDTO.getEmail()).ifPresent(existingUser::setEmail);
                    Optional.ofNullable(updateUserDTO.getFirstName()).ifPresent(existingUser::setFirstName);
                    Optional.ofNullable(updateUserDTO.getLastName()).ifPresent(existingUser::setLastName);
                    Optional.ofNullable(updateUserDTO.getAdress_street()).ifPresent(existingUser::setAdress_street);
                    Optional.ofNullable(updateUserDTO.getAdress_city()).ifPresent(existingUser::setAdress_city);
                    Optional.ofNullable(updateUserDTO.getAdress_postalCode()).ifPresent(existingUser::setAdress_postalCode);
                    Optional.of(updateUserDTO.getBalance()).ifPresent(existingUser::setBalance);
                    Optional.ofNullable(updateUserDTO.getGender()).ifPresent(existingUser::setGender);
                    Optional.ofNullable(updateUserDTO.getPhoto()).ifPresent(existingUser::setPhoto);

                    if (strRoles.isEmpty()) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roles.add(userRole);
                        existingUser.setRoles(roles);
                    }
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("User with id:" + updateUserDTO.getUserId() + " was not found!"));
    }


    // delete user account
    public ResponseEntity<String> deleteUser(DeleteUserDTO deleteUserDTO) {
        userRepository.findById(deleteUserDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        // Function to remove reviews referencing reviewed user
        List<Reviews> findReviews = reviewsRepository.findAll();
        for (int i = 0; i < findReviews.size(); i++) {
            if (findReviews.get(i).getReviewedUser().getId().equals(deleteUserDTO.getUserId())) {
                reviewsRepository.deleteById(findReviews.get(i).getId());
            }
            userRepository.deleteById(deleteUserDTO.getUserId());
        }
        return ResponseEntity.ok("User deleted");
    }


    public ResponseEntity<?> getUserFavouritesById(String userId) {
        User user = userRepository.findById(userId).get(); //find user with dto userId
        return ResponseEntity.ok(user.getFavouriteAuctions());//get favouriteAuction List

    }

    public ResponseEntity<?> setUserFavouritesById(String userId, String auctionId) {
        User foundUser = userRepository.findById(userId).get();// find user with dto userId
        Auction foundAuction = auctionRepository.findById(auctionId).get();// find auction with dto auctionId

        for (Auction auction : foundUser.getFavouriteAuctions()) {
            if (auction.getId().equals(auctionId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Auction already exist in favourites list...");
            }
        }
        foundUser.addToFav(foundAuction); //add auction to fave list
        userRepository.save(foundUser); //save user (updated favouriteList)
        return ResponseEntity.ok("Auction was added to favourite list.");
    }

    public ResponseEntity<?> deleteUserFavouritesById(String userId, String auctionId) {
        User foundUser = userRepository.findById(userId).get();

        // Loop through favorite list of user
        for (Auction auction : foundUser.getFavouriteAuctions()) {
            // If current auction id in loop match id in DTO
            if (auction.getId().equals(auctionId)) {
                // Save found auction before removing, this was necessary otherwise it doesnt work
                Auction foundAuctionToRemove = auction;
                foundUser.getFavouriteAuctions().remove(foundAuctionToRemove);
                userRepository.save(foundUser);
                return ResponseEntity.ok("Auction removed from favourite list.");
            }
        }
        return ResponseEntity.ok("Auction was not removed or does now exist in favourite-list");
    }


    // Remove auction from users favourite list. This function auto runs when creating an order.
    public void removeFavouriteAuctionFromUsers(String auctionId) {
        for (User user : userRepository.findAll()) {
            for (Auction auction : user.getFavouriteAuctions()) {
                if (auction != null) {
                    if (!user.getFavouriteAuctions().isEmpty() && auction.getId().equals(auctionId)) {
                        Auction foundAuction = auction;
                        user.getFavouriteAuctions().remove(foundAuction);
                        break;
                    }
                }
            }
            userRepository.save(user);
        }
    }


}

