package PolyUrl;

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
    public void sendSimpleMail(String recipientMail) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("benazetlaurent@gmail.com", "Sender"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipientMail, "User"));
            msg.setSubject("Test email from gcloud push queue");
            msg.setText("This is a test");
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String recipientMail = req.getParameter("recipientMail");
        sendSimpleMail(recipientMail);
        System.out.println(recipientMail);
    }

}


