package PolyUrl.ptitu;

import PolyUrl.storage.ContentType;
import PolyUrl.storage.Storage;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet(name = "SeePUrl", value = "/")
public class SeePUrl extends HttpServlet {
    private void addLogs(HttpServletRequest request) throws IOException {
        com.google.cloud.storage.Storage storage = StorageOptions.getDefaultInstance().getService();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String ptitu = request.getParameter("u");

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("PtitUStorage")
                .setFilter(StructuredQuery.PropertyFilter.eq("url", ptitu))
                .build();

        QueryResults<Entity> ptitus = datastore.run(query);

        Blob blob = storage.get("poly_url_logs", "globalLogs.txt");

        if (blob == null) {
            blob = storage.create(Blob
                            .newBuilder("poly_url_logs", "globalLogs.txt")
                            .build(),
                    os.toByteArray());
        }

        //add consultation logs
        if (ptitus.hasNext()) {
            Entity ptitU = ptitus.next();
            String url = ptitU.getString("url");
            ContentType contentType = ptitU.getBoolean("isImage") ? ContentType.IMAGE : ContentType.URL;
            String content = ptitU.getString("longUrl");
            String ownerMail = ptitU.getString("ownerMail");
            String IP = request.getRemoteAddr();
            Timestamp consultationDate = Timestamp.now();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("url", url);
            jsonObject.addProperty("contentType", contentType.toString());
            jsonObject.addProperty("content", content);
            jsonObject.addProperty("ownerMail", ownerMail);
            jsonObject.addProperty("IP", IP);
            jsonObject.addProperty("Date", consultationDate.toString());

            String fileContent = new String(blob.getContent(), UTF_8);

            WritableByteChannel channel = blob.writer();
            channel.write(ByteBuffer.wrap((fileContent + jsonObject.toString() + "\n").getBytes(UTF_8)));
            channel.close();
        }
    }

    //consult purl
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ptitu = request.getParameter("u");
        addLogs(request);

        //int id = CreatePUrl.shortURLtoID(request.getParameter("u"));
        String[] r = Storage.getLongUrlFromPtitU(ptitu);
        //0=url    1=isImage(String)  2=ownerMail
        if (r[1].equals("false")) {
            response.sendRedirect(r[0]);
        } else {
            //afficher l'image
            String mime = "image";

            response.setContentType(mime);

            InputStream in = new URL(r[0]).openStream();
            OutputStream out = response.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();
        }
    }
}

