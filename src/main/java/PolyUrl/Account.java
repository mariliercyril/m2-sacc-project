package PolyUrl;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Account", value = "/account")
public class Account extends HttpServlet {

    Gson gson = new Gson();



    //return the account information (not sure if this one is useful)
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //code
        response.getWriter().println("get account info");
    }

    //create a new account
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String admin = request.getParameter("admin");
        Role role = Boolean.parseBoolean(admin) ? Role.ADMIN : Role.USER;

        String subject = "PolyUrl Account creation";
        String message = "Welcome " + name + ", your account for PolyUrl has been created.";


        if (Storage.addAccount(new User(mail, role))) {
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

