package PolyUrl.account;

import PolyUrl.Role;
import PolyUrl.Storage;
import PolyUrl.User;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountWorker", value = "/accountworker")
public class AccountWorker extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String admin = request.getParameter("admin");
        Role role = Boolean.parseBoolean(admin) ? Role.ADMIN : Role.USER;

        String subject = "PolyUrl Account creation";
        String message = "Welcome " + name + ", your account for PolyUrl has been created.";

        System.out.println("name :" + name + " mail: " + mail + "admin: " + admin);

        if (Storage.addAccount(new User(mail, role, name))) {
            Queue queue = QueueFactory.getQueue("queue-mail");
            queue.add(TaskOptions.Builder.withUrl("/mailworker")
                    .param("senderMail", "mail@polyurl.appspotmail.com")
                    .param("recipientMail", mail)
                    .param("subject", subject)
                    .param("message", message));

            response.getWriter().println("Created new account");
        } else {
            response.getWriter().println("Can't create account, email is already taken");
        }
    }
}
