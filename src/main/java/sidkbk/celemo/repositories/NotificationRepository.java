package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Notification;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByToUserId(String toUserId);
}
