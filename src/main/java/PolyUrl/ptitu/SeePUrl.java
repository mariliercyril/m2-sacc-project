package PolyUrl.ptitu;

import PolyUrl.storage.ContentType;
import PolyUrl.storage.Storage;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        response.sendRedirect(r[0]);
    }
}

