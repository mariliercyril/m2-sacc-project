package PolyUrl.account;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Account", value = "/account")
public class Account extends HttpServlet {
    //create a new account
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String admin = request.getParameter("admin");


        Queue queue = QueueFactory.getQueue("queue-account");
        queue.add(TaskOptions.Builder.withUrl("/accountworker")
                .param("name", name)
                .param("mail", mail)
                .param("admin", admin));
    }


}

