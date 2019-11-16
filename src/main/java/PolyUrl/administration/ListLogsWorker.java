package PolyUrl.administration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListLogsWorker", value = "/listLogsWorker")
public class ListLogsWorker extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter("mail");

        System.out.println("Get list of logs for mail : " + mail);
    }
}
