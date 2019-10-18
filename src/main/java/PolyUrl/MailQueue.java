package PolyUrl;

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
        String recipientMail = req.getParameter("recipientMail");
        Queue queue = QueueFactory.getQueue("queue-mail");
        queue.add(TaskOptions.Builder.withUrl("/mailworker").param("recipientMail", recipientMail));
    }
}

