package sidkbk.celemo.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;


    @NotBlank
    @Email
    private String email;


    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;




}
