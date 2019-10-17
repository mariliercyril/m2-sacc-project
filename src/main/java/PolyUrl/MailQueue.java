package PolyUrl;

import com.google.gson.Gson;

import java.io.IOException;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MailQueue", value = "/mailqueue")
public class MailQueue extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Queue queue = QueueFactory.getQueue("queue-mail");
        queue.add(TaskOptions.Builder.withUrl("/mailworker").param("key", gson.toJson("placeholder")));
    }
}

