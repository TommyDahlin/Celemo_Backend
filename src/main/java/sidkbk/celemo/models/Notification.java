package sidkbk.celemo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private String title;
    @CreatedDate
    private Date createdAt = new Date();


    private String toUserId;

    public Notification(NotificationBuilder builder) {

        this.title = builder.getTitle();
        this.toUserId = builder.getToUserId();
    }

    public static class NotificationBuilder {
        private String toUserId;

        private String title;

        public NotificationBuilder(String toUserId, String title) {
            this.toUserId = toUserId;
            this.title = title;
        }

        public Notification build(){
            return new Notification(this);
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
