package PolyUrl.ptitu;

import PolyUrl.storage.ContentType;
import PolyUrl.storage.Storage;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.gson.JsonObject;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@WebServlet(name = "SeePUrl", value = "/")
public class SeePUrl extends HttpServlet {
    //consult purl
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String ptitu = request.getParameter("u");

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("PtitUStorage")
                .setFilter(StructuredQuery.PropertyFilter.eq("ptitu", ptitu))
                .build();

        QueryResults<Entity> ptitus = datastore.run(query);

        //add consultation logs
        if (ptitus.hasNext()) {
            Entity ptitU = ptitus.next();
            String url = ptitU.getString("url");
            ContentType contentType = ptitU.getBoolean("isImage") ? ContentType.IMAGE : ContentType.URL;
            String ownerMail = ptitU.getString("ownerMail");
            String IP = request.getRemoteAddr();
            Timestamp consultationDate = Timestamp.now();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("url", url);
            jsonObject.addProperty("contentType", contentType.toString());
            jsonObject.addProperty("ownerMail", ownerMail);
            jsonObject.addProperty("IP", IP);
            jsonObject.addProperty("Date", consultationDate.toString());
        }

        //int id = CreatePUrl.shortURLtoID(request.getParameter("u"));
        String[] r = Storage.getLongUrlFromPtitU(ptitu);
        //0=url    1=isImage(String)  2=ownerMail
        if(r[1].equals("false")){
            response.sendRedirect(r[0]);
        } else {
            //afficher l'image
            String mime = "image";
            if (mime == null) {
                //redirect when image not here
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("404 not found");
                return;
            }

            response.setContentType(mime);

            InputStream in = new URL(r[0]).openStream();
            OutputStream out = response.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();
        }
    }
}

