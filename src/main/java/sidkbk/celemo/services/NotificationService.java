package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.notification.NotifCreateDTO;
import sidkbk.celemo.models.Notification;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.NotificationRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    //post , create notification for user
    public ResponseEntity<Notification> createNotifUser(String toUserId, String title) {
        System.out.println(toUserId);
        User foundUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Notification notif = new Notification.NotificationBuilder(toUserId, title).build();

        return ResponseEntity.ok(notificationRepository.save(notif));
    }

    public ResponseEntity<Notification> createNotifUserDto(NotifCreateDTO notifCreateDTO) {

        Notification notif = new Notification.NotificationBuilder(notifCreateDTO.getToUserId(), notifCreateDTO.getTitle()).build();

        return ResponseEntity.ok(notificationRepository.save(notif));
    }


    public List<Notification> getAllNotificationsFromUser(String userId) {

        Optional<User> foundUser = userRepository.findById(userId);

        return notificationRepository.findByToUserId(foundUser.get().getId());

    }


    // delete All notifications from User
    public void deleteAllNotificationsFromUser(String userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));

        // Spara alla notiser i en lista
        List<Notification> allNotif = notificationRepository.findAll();
        // For loop genom alla notiser och kolla efter notiser som matchar med userid
        for (Notification notif : allNotif) {
            if (notif.getToUserId().equals(userId)) {
                notificationRepository.deleteById(notif.getId());
            }
        }
    }
}
