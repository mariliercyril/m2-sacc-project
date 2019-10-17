package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StoreLongUrl", value = "/storelongurl")
public class StoreLongUrl extends HttpServlet {

    Gson gson = new Gson();

    //add a long url
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //code
        // should store the url then ask CreatePUrl class for the purl
        response.getWriter().println("adds a long url and return the purl to access it");
    }
}

