package sidkbk.celemo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Reports {
    @Id
    private String id;


    /*


    //refactor this code to User

    @DBRef
    private User user;


    @DBRef
    listing listing



     */



}
