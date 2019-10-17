package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SeeDetails", value = "/seedetails")
public class SeeDetails extends HttpServlet {

    Gson gson = new Gson();


    //return the detail of one purl
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //code
        //should check if the user is the one that created the purl (or admin)
        response.getWriter().println("return the detail of one purl");
    }
}

