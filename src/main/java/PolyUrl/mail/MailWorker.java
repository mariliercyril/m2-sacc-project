package PolyUrl.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@WebServlet(name = "MailWorker", value = "/mailworker")
public class MailWorker extends HttpServlet {
    private void sendSimpleMail(String senderMail, String recipientMail, String subject, String message) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderMail, "Sender"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipientMail, "User"));

            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String senderMail = req.getParameter("senderMail");
        String recipientMail = req.getParameter("recipientMail");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        sendSimpleMail(senderMail, recipientMail, subject, message);

        System.out.println("Sender mail : " + senderMail);
        System.out.println("Recipient mail : " + recipientMail);
        System.out.println("Subject : " + subject);
        System.out.println("Message : " + message);
    }

}


