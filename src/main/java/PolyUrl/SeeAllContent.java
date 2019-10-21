package PolyUrl;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;


@WebServlet(name = "SeeAllContent", value = "/seeallcontent")
public class SeeAllContent extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Gson gson = new Gson();

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
			response.getWriter().println(getAllContent(optionalUser.get()));
		} else {
			response.getWriter().println("No registered user (or administrator) with this mail...");
		}
	}

	private String getAllContent(User user) {

		List<Ptitu> content = new ArrayList<>();

		switch(user.getRole()) {
			case USER:
				content = (Storage.getPtitu()).stream()
						.filter(p -> (p.getOwnerMail()).equals(user.getMail()))
						.collect(Collectors.toList());
			case ADMIN:
				content = (Storage.getPtitu());
		}

		return gson.toJson(content);
	}

}
