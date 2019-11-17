package PolyUrl.storage;

import PolyUrl.ptitu.CreatePUrl;
import PolyUrl.ptitu.Ptitu;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Storage;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
@WebServlet(name = "StoreImage", value = "/storeimage")
public class StoreImage extends HttpServlet {

    Gson gson = new Gson();

    //add an image

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

        String longurl = blobInfo.getMediaLink();

        String purl = CreatePUrl.idToShortURL(PolyUrl.storage.Storage.getPtituSize());
        String mail = request.getParameter("mail");
        PolyUrl.storage.Storage.addPtitu(new Ptitu(purl,longurl,mail, ContentType.IMAGE));
        response.getWriter().println("http://polyurl.appspot.com/?u="+purl);
    }
}

