package PolyUrl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MailWorker", value = "/mailworker")
public class MailWorker extends HttpServlet {
    private static Logger log = Logger.getLogger(MailWorker.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getParameter("key"));
    }

}


