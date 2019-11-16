package PolyUrl.administration;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListLogsQueue", value = "/listLogs")
public class ListLogs extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String mail = req.getParameter("mail");

        Queue queue = QueueFactory.getQueue("queue-list-logs");
        queue.add(TaskOptions.Builder.withUrl("/listLogsWorker")
                .param("mail", mail));
    }
}

