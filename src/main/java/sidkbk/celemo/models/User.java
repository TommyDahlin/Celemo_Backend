package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class User {

    @Id
    private String id;


/*
    @NotBlank
    //String

    @NotEmpty
    //List

    @NotEmpty
    //int
*/
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
    //dateofbirth
    @NotBlank(message = "dateOfBirth cannot be blank")
    private String dateOfBirth;
    @NotBlank(message = "email cannot be blank")
    private String email;

    private EGender gender;

    private String photo;
    @NotBlank(message = "firstName cannot be blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;
    //@NotEmpty(message = "gender cannot be blank")
   // private int chooseGender;
    private ERole role;
    @NotBlank(message = "adress_street cannot be blank")
    private String adress_street;
    @NotBlank(message = "adress_postalcode cannot be blank")
    private String adress_postalCode;
    @NotBlank(message = "adress_city cannot be blank")
    private String adress_city;

    @DBRef
    private ArrayList<String> favourites = new ArrayList<String>();


    private double balance;

    public User() {

    }




    public void addFavourites (String favouritesId){
        this.favourites.add(favouritesId);
    }
    public void removeFavourites (String favouritesId){
        this.favourites.remove(favouritesId);
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public Enum getGender() {
        return gender;
    }

    public Enum getRole() {
        return role;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public void setRole(ERole role) {
        this.role = role;
    }
}
