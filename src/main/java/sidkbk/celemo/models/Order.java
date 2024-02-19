package sidkbk.celemo.models;

import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    //@DBRef
    private String sellerId;
    //@DBRef
    private String buyerId;
    //@DBRef
    private String productTitle;
    //@DBRef
    private int endPrice;
    //@DBRef
    private String endDate;

    //calculating total ammount for the commission of 3%
    //private double commission = endPrice * 0.03;


    public Order() {
    }


    public String getId() {
        return id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public String getEndDate() {
        return endDate;
    }

  /*  public double getCommission() {
        return commission;
    }*/
}
