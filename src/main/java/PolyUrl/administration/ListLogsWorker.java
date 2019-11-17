package PolyUrl.administration;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.cloud.datastore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet(name = "ListLogsWorker", value = "/listLogsWorker")
public class ListLogsWorker extends HttpServlet {
    private String filterLogs(Blob blob, String mail, boolean isAdmin, ByteArrayOutputStream os, com.google.cloud.storage.Storage storage) throws IOException {
        String fileContent = new String(blob.getContent(), UTF_8);
        String[] fileLines = fileContent.split("\n");

        StringBuilder result = new StringBuilder();

        Blob blobLogs = storage.get("poly_url_logs", mail + "_globalLogs.txt");

        if (blobLogs == null) {
            blobLogs = storage.create(Blob
                            .newBuilder("poly_url_logs", mail + "_globalLogs.txt")
                            .build(),
                    os.toByteArray());
        }
        WritableByteChannel channel = blobLogs.writer();

        Gson gson = new Gson();

        for (String line : fileLines) {
            JsonObject jsonObject = gson.fromJson(line, JsonObject.class);
            if (!isAdmin) {
                if (jsonObject.get("ownerMail").toString().replaceAll("\"", "").equals(mail)) {
                    result.append(jsonObject.toString()).append("\n");
                }
            } else {
                result.append(jsonObject.toString()).append("\n");
            }
        }

        channel.write(ByteBuffer.wrap(result.toString().getBytes(UTF_8)));
        channel.close();

        return blobLogs.getMediaLink();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        com.google.cloud.storage.Storage storage = StorageOptions.getDefaultInstance().getService();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        String mail = request.getParameter("mail");

        System.out.println("Get list of logs for mail : " + mail);

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Account")
                .setFilter(StructuredQuery.PropertyFilter.eq("mail", mail))
                .build();

        QueryResults<Entity> accounts = datastore.run(query);

        if (accounts.hasNext()) {
            Blob blob = storage.get("poly_url_logs", "globalLogs.txt");
            if (blob == null) {
                blob = storage.create(Blob
                                .newBuilder("poly_url_logs", "globalLogs.txt")
                                .build(),
                        os.toByteArray());
            }
            Entity account = accounts.next();
            boolean isAdmin = account.getBoolean("role");

            String link = filterLogs(blob, mail, isAdmin, os, storage);

            String subject = "Logs for all your ptit-u";
            String message = "Hello " + account.getString("name") + ", you requested the logs for all your ptit-u. It is downloadable at the following link : " + link;

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
