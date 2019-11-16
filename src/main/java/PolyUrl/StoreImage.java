package PolyUrl;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormat;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormatter;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Storage;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

@MultipartConfig
@WebServlet(name = "StoreImage", value = "/storeimage")
public class StoreImage extends HttpServlet {

    Gson gson = new Gson();

    //add an image
    /*@Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CloudStorageHelper cloudStorageHelper = new CloudStorageHelper();
            String x = cloudStorageHelper.getImageUrl(request,response,"poly_url_images");
            //String x = cloudStorageHelper.uploadFile(request.getPart("file"),"poly_url_images");
            response.getWriter().println(x);
        }catch (Exception e){
            response.getWriter().println("Erreur : "+e.getMessage());
        }

        //CloudStorageHelper cloudStorageHelper = new CloudStorageHelper();
        //String resp = cloudStorageHelper.uploadFile(request.getPart(),"poly_url_images");

        // should store image then ask CreatePUrl class for the purl
        //response.getWriter().println(resp);
    }*/

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        InputStream is = request.getPart("file").getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] readBuf = new byte[4096];
        while (is.available() > 0) {
            int bytesRead = is.read(readBuf);
            os.write(readBuf, 0, bytesRead);
        }

        BlobInfo blobInfo = storage.create(BlobInfo
                        .newBuilder("poly_url_images", request.getPart("file").getSubmittedFileName())
                        .build(),
                os.toByteArray());

        String purl = CreatePUrl.idToShortURL(PolyUrl.Storage.getPtituSize());
        String longurl = request.getParameter(blobInfo.getMediaLink());
        String mail = request.getParameter("mail");
        PolyUrl.Storage.addPtitu(new Ptitu(purl,longurl,mail,ContentType.IMAGE));
        response.getWriter().println("http://polyurl.appspot.com/?u="+purl);
    }
}

