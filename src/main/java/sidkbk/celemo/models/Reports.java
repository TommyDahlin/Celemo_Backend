package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Reports {
    @Id
    private String id;


    @CreatedDate
    private String createdAt;


    private String content;

    public Reports() {
    }

    public String getContent() {
        return content;
    }


/*

    //Evarything from here to reference to userid and listingid,
    @DBRef
    private User user;

    @DBRef
    private listing listing;


 */











}
