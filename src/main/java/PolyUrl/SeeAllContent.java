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


@WebServlet(name = "SeeAllContent", value = "/seeallcontent")
public class SeeAllContent extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * For <b>registered user</b>, return the list of its own content ({@code Ptitu}).
	 * For <b>administrator</b>, return the list of all contents.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String mail = request.getParameter("mail");

		Optional<User> optionalUser = (Storage.getAccounts()).stream()
				.filter((a) -> (a.getMail()).equals(mail))
				.findFirst();

		if (optionalUser.isPresent()) {
			Queue queue = QueueFactory.getQueue("queue-administration");
			queue.add(TaskOptions.Builder.withUrl("/administrationworker")
					.param("mail", mail));
		} else {
			response.getWriter().println("No registered user (or administrator) with this mail...");
		}
	}

}
