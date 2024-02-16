package sidkbk.celemo.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Account {

    @DBRef
    public String sellerId;

}
