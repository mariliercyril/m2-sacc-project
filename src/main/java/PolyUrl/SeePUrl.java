package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SeePUrl", value = "/seepurl")
public class SeePUrl extends HttpServlet {

    Gson gson = new Gson();


    //consult purl
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //code
        response.getWriter().println("no response here, should redirect to the long url/image");
    }
}

