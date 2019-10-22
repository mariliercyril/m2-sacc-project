package PolyUrl;

import java.io.IOException;

import java.util.Optional;

import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet(name = "AdministrationWorker", value = "/administrationworker")
public class AdministrationWorker extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Gson gson = new Gson();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String mail = request.getParameter("mail");
		String pUrl = request.getParameter("purl");

		String information = (pUrl == null) ? getAllContent(mail) : getDetails(mail, pUrl);

		response.getWriter().println(information);

		System.out.println("Mail : " + mail);
		System.out.println("PUrl : " + pUrl);
    }

	private String getAllContent(String mail) {

		String content = new String();

		Optional<User> optionalUser = (Storage.getAccounts()).stream()
				.filter((a) -> (a.getMail()).equals(mail))
				.findFirst();

		User user = optionalUser.get();
		switch(user.getRole()) {
			case USER:
				content = gson.toJson((Storage.getPtitu()).stream()
						.filter(p -> (p.getOwnerMail()).equals(user.getMail()))
						.collect(Collectors.toList()));
			case ADMIN:
				content = gson.toJson((Storage.getPtitu()));
		}
		content = "The all content list returned.";

		return content;
	}

	private String getDetails(String mail, String pUrl) {

		String details = new String();

		Optional<User> optionalUser = (Storage.getAccounts()).stream()
				.filter((a) -> (a.getMail()).equals(mail))
				.findFirst();

		Optional<Ptitu> optionalPtitu = (Storage.getPtitu()).stream()
				.filter((p) -> (p.getUrl()).equals(pUrl))
				.findFirst();

		User user = optionalUser.get();
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

		return details;
    }

}
