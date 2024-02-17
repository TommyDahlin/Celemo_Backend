package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "bids")
public class Bids {
    @Id
    private String id;

    @CreatedDate
    public LocalDate timestamp = LocalDate.now();




    /*
    This code from here waiting for finishing reference till user and listing
    @DBRef
    private Users users;


    @DBRef
    private listing listing



     */



}
