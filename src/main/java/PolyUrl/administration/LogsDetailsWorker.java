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

@WebServlet(name = "LogsDetailsWorker", value = "/logsDetailsWorker")
public class LogsDetailsWorker extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mail = request.getParameter("mail");
        String ptitu = request.getParameter("ptitu");

        System.out.println("Get list of logs for the ptit-u : " + ptitu + " with mail " + mail);

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Account")
                .setFilter(StructuredQuery.PropertyFilter.eq("mail", mail))
                .build();

        QueryResults<Entity> accounts = datastore.run(query);

        if (accounts.hasNext()) {
            Entity account = accounts.next();
            boolean isAdmin = account.getBoolean("role");
            String subject = "Logs your chosen ptit-u";
            String message = "Hello " + account.getString("name") + ", you requested the logs for the ptit-u " + ptitu + ".";

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
