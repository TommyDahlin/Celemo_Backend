package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
