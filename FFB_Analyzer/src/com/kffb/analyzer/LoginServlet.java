package com.kffb.analyzer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kffb.analyzer.dto.User;
import com.kffb.analyzer.service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/LoginServlet", "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username, password;
		username = request.getParameter("username");
		password = request.getParameter("password");
		
		LoginService loginService = new LoginService();
		boolean result = loginService.authenticate(username, password);
		if (result) {
			User user = loginService.getUserDetails(username);
			request.setAttribute("user", user);
			// response.sendRedirect relies on telling the browser to change to next url
			// dispatcher
			RequestDispatcher dispatcher = request.getRequestDispatcher("/templates/success.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("/templates/login.jsp");
		}
	}

}
