package PolyUrl.mail;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MailQueue", value = "/mailqueue")
public class MailQueue extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String senderMail = req.getParameter("senderMail");
        String recipientMail = req.getParameter("recipientMail");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        Queue queue = QueueFactory.getQueue("queue-mail");
        queue.add(TaskOptions.Builder.withUrl("/mailworker")
                .param("senderMail", (senderMail == null) ? "mail@polyurl.appspotmail.com" : senderMail)
                .param("recipientMail", recipientMail)
                .param("subject", (subject == null) ? "default subject" : subject)
                .param("message", (message == null) ? "default message" : message));
    }
}

