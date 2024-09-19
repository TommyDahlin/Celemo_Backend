package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.notification.NotifCreateDTO;
import sidkbk.celemo.models.Notification;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.NotificationRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    //post , create notification for user
    public ResponseEntity<Notification> createNotifUser(NotifCreateDTO notifDTO) {
        User foundUser = userRepository.findById(notifDTO.getToUserId()) // this could be a problem
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Notification notif = new Notification.NotificationBuilder();


        return ResponseEntity.ok(notificationRepository.save(notif));
    }

    // get All notifications from User
    public List<Notification> getAllNotificationsFromUser(String userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));
        // Skapa en tom lista för hittade notiser
        List<Notification> foundNotif= new ArrayList<>();
        // Spara alla notiser i en lista
        List<Notification> allNotif = notificationRepository.findAll();
        // For loop genom alla notiser och kolla efter notiser som matchar med userid
        for (Notification notif : allNotif){
            if (notif.getToUserId().equals(userId)) {
                foundNotif.add(notif);
            }
        }
        return foundNotif;
    }
    // delete All notifications from User
    public void deleteAllNotificationsFromUser(String userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));

        // Spara alla notiser i en lista
        List<Notification> allNotif = notificationRepository.findAll();
        // For loop genom alla notiser och kolla efter notiser som matchar med userid
        for (Notification notif : allNotif){
            if (notif.getToUserId().equals(userId)) {
                notificationRepository.deleteById(notif.getId());
            }
        }
    }
}
