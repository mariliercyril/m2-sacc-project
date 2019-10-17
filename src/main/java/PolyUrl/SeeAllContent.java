package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SeeAllContent", value = "/seeallcontent")
public class SeeAllContent extends HttpServlet {

    Gson gson = new Gson();


    //return the list of all the content
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //code
        response.getWriter().println("return all the content of the user (all users if he is admin)");
    }
}

