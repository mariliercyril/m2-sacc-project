package PolyUrl.storage;

import PolyUrl.ptitu.CreatePUrl;
import PolyUrl.ptitu.Ptitu;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StoreLongUrl", value = "/storelongurl")
public class StoreLongUrl extends HttpServlet {

    //add a long url
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //code
        String purl = CreatePUrl.idToShortURL(Storage.getPtituSize());
        String longurl = request.getParameter("longurl");
        String mail = request.getParameter("mail");
        Storage.addPtitu(new Ptitu(purl, longurl, mail, ContentType.URL));
        response.getWriter().println("http://polyurl.appspot.com/?u=" + purl);
    }
}

