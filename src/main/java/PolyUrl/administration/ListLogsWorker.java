package PolyUrl.administration;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.cloud.datastore.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ListLogsWorker", value = "/listLogsWorker")
public class ListLogsWorker extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mail = request.getParameter("mail");

        System.out.println("Get list of logs for mail : " + mail);

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Account")
                .setFilter(StructuredQuery.PropertyFilter.eq("mail", mail))
                .build();

        QueryResults<Entity> accounts = datastore.run(query);

        if (accounts.hasNext()) {
            Entity account = accounts.next();
            boolean isAdmin = account.getBoolean("role");
            String subject = "Logs for all your ptit-u";
            String message = "Hello " + account.getString("name") + ", you requested the logs for all your ptit-u.";

            Queue queue = QueueFactory.getQueue("queue-mail");
            queue.add(TaskOptions.Builder.withUrl("/mailworker")
                    .param("senderMail", "mail@polyurl.appspotmail.com")
                    .param("recipientMail", mail)
                    .param("subject", subject)
                    .param("message", message));

            response.getWriter().println("Created new account");
        }

    }
}
