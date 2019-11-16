package PolyUrl.administration;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogsDetailsQueue", value = "/logsDetails")
public class LogsDetails extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String mail = req.getParameter("mail");
        String ptitu = req.getParameter("ptitu");

        Queue queue = QueueFactory.getQueue("queue-logs-details");
        queue.add(TaskOptions.Builder.withUrl("/logsDetailsWorker")
                .param("mail", mail)
                .param("ptitu", ptitu));
    }
}

