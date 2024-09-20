package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Notification;
import sidkbk.celemo.services.NotificationService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notif")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    // Get all auctions from userid
    @GetMapping("/find/all/user/{userId}")
    public List<Notification> getAllNotifFromUser(@PathVariable("userId") String userId) {



           return notificationService.getAllNotificationsFromUser(userId);

    }
    // Delete all auctions from userid
    @DeleteMapping("/delete/all/user/{userId}")
    public void deleteAllNotifFromUser(@PathVariable("userId") String userId) {
        try {
            notificationService.deleteAllNotificationsFromUser(userId);
        } catch (EntityNotFoundException e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have any notifications...");
        }
    }
}
