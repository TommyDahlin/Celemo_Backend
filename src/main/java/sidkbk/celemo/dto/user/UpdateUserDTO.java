package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;
import sidkbk.celemo.models.EGender;

import java.util.HashSet;
import java.util.Set;

public class UpdateUserDTO {
    @NotBlank
    private String userId;
    private String username;
    private String password;
    private String dateOfBirth;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> usersRoles = new HashSet<>();
    private EGender gender;
    private String adress_street;
    private String adress_postalCode;
    private String adress_city;
    private String photo;
    private double balance;


    public String getUserId() {
        return userId;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
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

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
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
