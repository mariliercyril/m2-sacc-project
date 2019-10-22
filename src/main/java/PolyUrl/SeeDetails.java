package PolyUrl;

import java.io.IOException;

import java.util.Optional;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "SeeDetails", value = "/seedetails")
public class SeeDetails extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * For <b>registered user</b>, return the details of all accesses for one of his {@code Ptitu}.
	 * For <b>administrator</b>, return the details of any content.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String mail = request.getParameter("mail");
		String pUrl = request.getParameter("purl");

		Optional<User> optionalUser = (Storage.getAccounts()).stream()
				.filter((a) -> (a.getMail()).equals(mail))
				.findFirst();

		if (optionalUser.isPresent()) {
			Optional<Ptitu> optionalPtitu = (Storage.getPtitu()).stream()
					.filter((p) -> (p.getUrl()).equals(pUrl))
					.findFirst();
			if (optionalPtitu.isPresent()) {
				Queue queue = QueueFactory.getQueue("queue-administration");
				queue.add(TaskOptions.Builder.withUrl("/administrationworker")
						.param("mail", mail)
						.param("purl", pUrl));
			} else {
				response.getWriter().println("No ptit-u created with this URL...");
			}
		} else {
			response.getWriter().println("No registered user (or administrator) with this mail...");
		}
	}

}
