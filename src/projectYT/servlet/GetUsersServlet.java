package projectYT.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import projectYT.dao.UserDAO;
import projectYT.model.User;


public class GetUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Ucitavanje usera zapoceto");
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		String orderBy="";
		int sort = Integer.parseInt(request.getParameter("sort"));
		switch (sort) {
		case 1:
			orderBy = " ORDER BY userName ASC";
			break;
		case 2:
			orderBy = " ORDER BY userName DESC";
			break;
		case 3:
			orderBy = " ORDER BY firstName ASC";
			break;
		case 4:
			orderBy = " ORDER BY firstName DESC";
			break;
		case 5:
			orderBy = " ORDER BY lastName ASC";
			break;
		case 6:
			orderBy = " ORDER BY lastName DESC";
			break;
		case 7:
			orderBy = " ORDER BY email ASC";
			break;
		case 8:
			orderBy = " ORDER BY email DESC";
			break;
		case 9:
			orderBy = " ORDER BY userType ASC";
			break;
		case 10:
			orderBy = " ORDER BY userType DESC";
			break;
		case 11:
			orderBy = " ORDER BY blocked ASC";
			break;
		case 12:
			orderBy = " ORDER BY blocked DESC";
			break;
		}
		
		System.out.println(orderBy);
		String search = request.getParameter("search");
		ArrayList<User> users = new ArrayList<>();
		if(search.equals("true")){
			String input = request.getParameter("input");
			users =UserDAO.searchUsers(input,orderBy);
		}else {
			users =UserDAO.getAll(orderBy);
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("users", users);
		data.put("loggedInUser", loggedInUser);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje usera: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
