package sidkbk.celemo.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import sidkbk.celemo.models.EGender;

import java.util.HashSet;
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
    @NotBlank(message = "dateOfBirth cannot be blank")
    private String dateOfBirth;
    @NotBlank(message = "firstName cannot be blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;
    private String photo;
    private Set<String> usersRoles = new HashSet<>();
    private EGender gender;
    @NotBlank(message = "adress_street cannot be blank")
    private String adress_street;
    @NotBlank(message = "adress_postalcode cannot be blank")
    private String adress_postalCode;
    @NotBlank(message = "adress_city cannot be blank")
    private String adress_city;


    public String getPhoto() {
        return photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<String> getUsersRoles() {
        return usersRoles;
    }

    public EGender getGender() {
        return gender;
    }

    public String getAdress_street() {
        return adress_street;
    }

    public String getAdress_postalCode() {
        return adress_postalCode;
    }

    public String getAdress_city() {
        return adress_city;
    }
}
