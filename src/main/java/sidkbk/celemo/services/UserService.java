package sidkbk.celemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.user.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.*;
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


    // HELENA:
    // är det här regsiter eller inte?
    // det ser ut som ett ihopkok av createUser och register eftersom ni blandar in roller
    // om det här är en metod för att registrera en user så bör metodsignaturen spegla det
    // den bör inte heller ligga i en UserService utan i så fall => AuthService


    // create/add/post user account
    public User createUser(CreateUserDTO createUserDTO) {
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
        if (user.getGender() == null) {
            throw new RuntimeException("ERROR: no gender");
        } else if (user.getGender().equals("MALE")) { //string to enum
            user.setGender(EGender.MALE);
        } else if (user.getGender().equals("FEMALE")) {//string to enum
            user.setGender(EGender.FEMALE);
        }

        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = createUserDTO.getUsersRoles();
        if (strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: role is not found"));
            roles.add(userRole);
            user.setRoles(roles);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Admin Role couldn't be found"));
                        roles.add(adminRole);
                        user.setRoles(roles);
                    }
                    case "USER" -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role couldn't be found"));
                        roles.add(userRole);
                        user.setRoles(roles);
                    }
                }
            });

        }

        return userRepository.save(user);
    }

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
    public String getUserFilter(FindUserIdandFilterDTO findUserIdandFilterDTO) { //userId and filter, filter can be grade, username, firstName, lastName
        User user = userRepository.findById(findUserIdandFilterDTO.getUserId()).get();
        return user.getFilter(findUserIdandFilterDTO.getFilter());

    }

    // get/find user account using id
    public Optional<User> getUserById(FindUserIdDTO findUserIdDTO) {
        return userRepository.findById(findUserIdDTO.getUserId());
    }

    //HELENA:
    // ska en user verkligen få uppdatera sin roll? det känns inte så bra...
    // fundera på:
    // vad ska en admin få uppdatera på en user?
    // vad ska en user få uppdatera om sig själv?

    // dessutom kan ni göra det lite snyggare och ta bort era 1000 ifs med det här :)
    // Optional.ofNullable(updateUserDTO.getUsername()).ifPresent(existingUser::setUsername);
    // går att göra likadant för varje rad

    // PUT/update user account. checks that new value isn't empty before adding. If something is empty then it will throw EntityNotFoundException
    public User updateUser(UpdateUserDTO updateUserDTO) {
        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = updateUserDTO.getUsersRoles();
        return userRepository.findById(updateUserDTO.getUserId())
                .map(existingUser -> {
                    if (updateUserDTO.getUsername() != null) {
                        existingUser.setUsername(updateUserDTO.getUsername());
                    }
                    if (updateUserDTO.getPassword() != null) {
                        existingUser.setPassword(updateUserDTO.getPassword());
                    }
                    if (updateUserDTO.getDateOfBirth() != null) {
                        existingUser.setDateOfBirth(updateUserDTO.getDateOfBirth());
                    }
                    if (updateUserDTO.getEmail() != null) {
                        existingUser.setEmail(updateUserDTO.getEmail());

                    }
                    if (updateUserDTO.getFirstName() != null) {
                        existingUser.setFirstName(updateUserDTO.getFirstName());
                    }
                    if (updateUserDTO.getLastName() != null) {
                        existingUser.setLastName(updateUserDTO.getLastName());
                    }
                    if (updateUserDTO.getAdress_street() != null) {
                        existingUser.setAdress_street(updateUserDTO.getAdress_street());
                    }
                    if (updateUserDTO.getAdress_city() != null) {
                        existingUser.setAdress_city(updateUserDTO.getAdress_city());
                    }
                    if (updateUserDTO.getAdress_postalCode() != null) {
                        existingUser.setAdress_postalCode(updateUserDTO.getAdress_postalCode());
                    }
                    if (updateUserDTO.getBalance() != 0.0) {
                        existingUser.setBalance(updateUserDTO.getBalance());
                    }
                    if (updateUserDTO.getGender() != null) {
                        existingUser.setGender(updateUserDTO.getGender());
                    }
                    if (updateUserDTO.getPhoto() != null) {
                        existingUser.setPhoto(updateUserDTO.getPhoto());
                    }

                    if (strRoles.isEmpty()) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roles.add(userRole);
                        existingUser.setRoles(roles);
                    } else {
                        strRoles.forEach(role -> {
                            switch (role) {
                                case "ADMIN" -> {
                                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                            .orElseThrow(() -> new RuntimeException("Error: Admin Role couldn't be found"));
                                    roles.add(adminRole);
                                    existingUser.setRoles(roles);
                                }
                                case "USER" -> {
                                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                            .orElseThrow(() -> new RuntimeException("Error: Role couldn't be found"));
                                    roles.add(userRole);
                                    existingUser.setRoles(roles);
                                }
                            }
                        });

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


    public ResponseEntity<?> getUserFavouritesById(FindUserFavouritesDTO findUserFavouritesDTO) {
        User user = userRepository.findById(findUserFavouritesDTO.getUserId()) //find user with dto userId
                .orElseThrow(() -> new RuntimeException("User does not exist"));// if user cant be found
        return ResponseEntity.ok(user.getFavouriteAuctions());//get favouriteAuction List

    }

    public ResponseEntity<?> setUserFavouritesById(ModifyUserFavouritesDTO favouritesDTO) {
        User foundUser = userRepository.findById(favouritesDTO.getUserId())// find user with dto userId
                .orElseThrow(() -> new RuntimeException("User does not exist")); // if user cant be found
        Auction foundAuction = auctionRepository.findById(favouritesDTO.getAuctionId())// find auction with dto auctionId
                .orElseThrow(() -> new RuntimeException("Auction does not exist"));// if auction cant be found

        for (Auction auction : foundUser.getFavouriteAuctions()) {
            if (auction.getId().equals(favouritesDTO.getAuctionId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Auction already exist in favourites list...");
            }
        }
        foundUser.addToFav(foundAuction); //add auction to fave list
        userRepository.save(foundUser); //save user (updated favouriteList)
        return ResponseEntity.ok("Auction was added to favourite list.");
    }

    public ResponseEntity<?> deleteUserFavouritesById(ModifyUserFavouritesDTO deleteFavouritesDto) {
        User foundUser = userRepository.findById(deleteFavouritesDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        // Loop through favorite list of user
        for (Auction auction : foundUser.getFavouriteAuctions()) {
            // If current auction id in loop match id in DTO
            if (auction.getId().equals(deleteFavouritesDto.getAuctionId())) {
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
                    if (!user.getFavouriteAuctions().isEmpty() && auction.getId().equals(auctionId)) {
                        Auction foundAuction = auction;
                        user.getFavouriteAuctions().remove(foundAuction);
                        break;
                    }
                }
                userRepository.save(user);
        }
    }
}

