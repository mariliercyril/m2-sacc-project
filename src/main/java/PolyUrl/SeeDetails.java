package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Optional;


@WebServlet(name = "SeeDetails", value = "/seedetails")
public class SeeDetails extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Gson gson = new Gson();

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
			response.getWriter().println(getDetails(optionalUser.get(), pUrl));
		} else {
			response.getWriter().println("No registered user (or administrator) with this mail...");
		}
	}

	private String getDetails(User user, String pUrl) {

		String details = new String();

		Optional<Ptitu> optionalPtitu = (Storage.getPtitu()).stream()
				.filter((p) -> (p.getUrl()).equals(pUrl))
				.findFirst();

		if (optionalPtitu.isPresent()) {
			Ptitu ptitu = optionalPtitu.get();
			switch(user.getRole()) {
				case USER:
					if ((ptitu.getOwnerMail()).equals(user.getMail())) {
						details = gson.toJson(ptitu);
					} else {
						details = "The user registered with this mail did not create the purl in question.";
					}
				case ADMIN:
					details = gson.toJson(ptitu);
			}
		} else {
			details = "No ptit-u created with this URL...";
		}

		return details;
    }

}
