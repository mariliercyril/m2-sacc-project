package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StoreImage", value = "/storeimage")
public class StoreImage extends HttpServlet {

    Gson gson = new Gson();

    //add an image
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //code
        // should store image then ask CreatePUrl class for the purl
        response.getWriter().println("adds an image and returns the purl to access it");
    }
}

