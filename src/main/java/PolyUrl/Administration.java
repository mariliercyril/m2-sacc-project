package PolyUrl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(name = "Administration", value = "/administration")
public class Administration extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Gson gson = new Gson();

	/**
	 * Gets the list of all the "ptit-u" of a registered user.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String mail = request.getParameter("mail");

		for (User account : (Storage.getAccounts())) {
			if ((account.getMail()).equals(mail) && (account.getRole()).equals(Role.USER)) {
				response.getWriter().println(getAllMyPtitu(mail));
			}
		}
	}

	private List<Ptitu> getAllMyPtitu(String mail) {

		List<Ptitu> allMyPtitu = new ArrayList<>();

		for (Ptitu ptitu : Storage.getPtitu()) {
			if ((ptitu.getOwnerMail()).equals(mail)) {
				allMyPtitu.add(ptitu);
			}
		}

		return allMyPtitu;
	}

}
