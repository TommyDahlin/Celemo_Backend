package sidkbk.celemo.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailHandler {

    @Autowired
    private JavaMailSender mailSender;


    // anropa från timerService
    public String sendMail(String mail, String auctionName, double price) {
        sendSimpleMessage(mail, "Celemo info", "En av dina auktioner har avslutats: " + auctionName + " såldes för: " + price);
        return "E-post skickat!";
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
