package sidkbk.celemo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Account {

    @Id
    private String id;

    @DBRef
    public String sellerId;

    @NotBlank
    //String

    @NotEmpty
    //List

    @NotEmpty
    //int

}
