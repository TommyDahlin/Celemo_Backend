package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;
import sidkbk.celemo.models.EGender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CreateUserDTO {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
    //dateofbirth
    @NotBlank(message = "dateOfBirth cannot be blank")
    private String dateOfBirth;
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotBlank(message = "firstName cannot be blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;
    private Set<String> usersRoles = new HashSet<>();
    private EGender gender;
    @NotBlank(message = "adress_street cannot be blank")
    private String adress_street;
    @NotBlank(message = "adress_postalcode cannot be blank")
    private String adress_postalCode;
    @NotBlank(message = "adress_city cannot be blank")
    private String adress_city;

    private ArrayList<String> favouriteAuctions = new ArrayList<String>();

    public String getUsername() {
        return username;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getUsersRoles() {
        return usersRoles;
    }

    public void setUsersRoles(Set<String> usersRoles) {
        this.usersRoles = usersRoles;
    }

    public String getAdress_street() {
        return adress_street;
    }

    public void setAdress_street(String adress_street) {
        this.adress_street = adress_street;
    }

    public String getAdress_postalCode() {
        return adress_postalCode;
    }

    public void setAdress_postalCode(String adress_postalCode) {
        this.adress_postalCode = adress_postalCode;
    }

    public String getAdress_city() {
        return adress_city;
    }

    public void setAdress_city(String adress_city) {
        this.adress_city = adress_city;
    }

}

