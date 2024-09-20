package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.regex.Pattern;

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

    private String photo = "dummy.png";
    @NotBlank(message = "firstName cannot be blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @NotBlank(message = "adress_street cannot be blank")
    private String adress_street;
    @NotBlank(message = "adress_postalcode cannot be blank")
    private String adress_postalCode;
    @NotBlank(message = "adress_city cannot be blank")
    private String adress_city;


    @DBRef
    private List<Auction> favouriteAuctions = new ArrayList<>();


    private double balance = 10000.0;

    private double grade = 0d;

    @CreatedDate
    private Date createdAt = new Date();

    public User() {
    }

    public User(String username, String email, String password, String dateOfBirth, String photo, String firstName, String lastName, String adress_street, String adress_postalCode, String adress_city) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress_street = adress_street;
        this.adress_postalCode = adress_postalCode;
        this.adress_city = adress_city;
    }

    public void addToFav(Auction auction) {
        favouriteAuctions.add(auction);
    } // this could be a problem

    public void removeFromFav(Auction auction) {

        favouriteAuctions.remove(auction);
    }


    //return variable and change to string if necessary
    public String getFilter(String filter) {
        filter.toLowerCase();
        switch (filter) {
            case "grade":
                Double a = this.getGrade();
                return a.toString();

            case "firstname":
                return this.getFirstName();

            case "lastname":
                return this.getLastName();

            case "username":
                return this.getUsername();

            default:
                return null;

        }

    }


    // det här ingår i Security genom BCrypt, jättebra att ni tänker på det men kom ihåg
    // att fundera över vad ni lägger er tid på nästa gång
    public void isPasswordCorrect(User user) {


        Pattern UpperCasePattern = Pattern.compile("[A-Z ]");

        if (user.getPassword().length() < 8) {
            throw new RuntimeException("ERROR: password lengt must be atleast 8 characters.");
        }
        if (!UpperCasePattern.matcher(user.getPassword()).find()) {
            throw new RuntimeException("ERROR: password must contain atleast one upperCase.");
        }

    }

    public boolean userHasRole(Role role) {
        return roles.contains(role);
    }


    public String getUsername() {
        return username;
    }

    public String getUsernameAndEmail() {

        return getUsername() + " , " + getEmail();
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

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


    public void setFavouriteAuctions(List<Auction> favouriteAuctions) {
        this.favouriteAuctions = favouriteAuctions;
    }

    public List<Auction> getFavouriteAuctions() {
        return favouriteAuctions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
