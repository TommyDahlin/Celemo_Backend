package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.models.EGender;
import sidkbk.celemo.models.ERole;
import sidkbk.celemo.models.Role;
import sidkbk.celemo.models.User;
import sidkbk.celemo.payload.request.SigninRequest;
import sidkbk.celemo.payload.request.SignupRequest;
import sidkbk.celemo.payload.response.MessageResponse;
import sidkbk.celemo.payload.response.UserInfoResponse;
import sidkbk.celemo.repositories.RoleRepository;
import sidkbk.celemo.repositories.UserRepository;
import sidkbk.celemo.security.jwt.JwtUtils;
import sidkbk.celemo.security.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    // sign in
    @CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // jwt token without cookie

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // jwt in cookie
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    // sign up/register new user
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signupRequest){
        if (userRepository.existsByUsername((signupRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username already exists!"));
        }
        if (userRepository.existsByEmail((signupRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: email already exists!"));
        }

        // create user account

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()), signupRequest.getDateOfBirth(), signupRequest.getPhoto(), signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getAdress_street(), signupRequest.getAdress_postalCode(), signupRequest.getAdress_city());
        user.setGender(signupRequest.getGender());
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        //checks that gender isn't null
        if (user.getGender() == null) {
            throw new RuntimeException("ERROR: no gender");
        } else if (user.getGender().equals("MALE")) { //string to enum
            user.setGender(EGender.MALE);
        } else if (user.getGender().equals("FEMALE")) {//string to enum
            user.setGender(EGender.FEMALE);
        }
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: roles is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(adminRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }



}
