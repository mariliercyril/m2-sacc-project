package PolyUrl.administration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogsDetailsWorker", value = "/logsDetailsWorker")
public class LogsDetailsWorker extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter("mail");
        String ptitu = request.getParameter("ptitu");

        System.out.println("Get list of logs for mail : " + mail + " for ptitu " + ptitu);
    }
}
