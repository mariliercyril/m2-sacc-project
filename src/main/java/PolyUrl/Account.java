package PolyUrl;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Account", value = "/account")
public class Account extends HttpServlet {

    Gson gson = new Gson();


    //return the account information (not sure if this one is useful)
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //code
        response.getWriter().println("get account info");
    }

    //create a new account
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //code
        response.getWriter().println("create new account");
    }
}

