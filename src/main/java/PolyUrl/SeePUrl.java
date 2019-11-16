package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SeePUrl", value = "/")
public class SeePUrl extends HttpServlet {

    Gson gson = new Gson();


    //consult purl
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //code
        //int id = CreatePUrl.shortURLtoID(request.getParameter("u"));
        String url = Storage.getLongUrlFromPtitU(request.getParameter("u"));
        response.getWriter().println(url);
        //response.sendRedirect(url);
    }
}

