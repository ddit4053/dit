package kr.or.ddit.board.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/*")
public class BoardFrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo(); // /community, /info, /guide 등을 가져옴
		String contentPage = null;
		String forwardPage = null;
		
		// pathInfo에 따라 contentPage와 forwardPage 설정
		if(pathInfo == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		switch (pathInfo) {
			case "/community":
	            contentPage = "/WEB-INF/view/users/users_boardList.jsp";
	            forwardPage = "/WEB-INF/view/users/users_board.jsp";
	            break;
	            
			case "/community/review/list":
				contentPage = "/WEB-INF/view/users/users_boardList.jsp";
	            forwardPage = "/WEB-INF/view/users/users_board.jsp";
	            break;    
	            
	        case "/info":
	        	contentPage = "/WEB-INF/view/users/users_boardList.jsp";
	            forwardPage = "/WEB-INF/view/users/users_board.jsp";
	            break;
	            
	        case "/guide":
	        	contentPage = "/WEB-INF/view/users/guide/facilities.jsp";
	            forwardPage = "/WEB-INF/view/users/guide/guide.jsp";
	            break;
	            
	        default:
	            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	            return;
	    }
		
		// contentPage 설정
        req.setAttribute("contentPage", contentPage);
       
        req.getServletContext().getRequestDispatcher(forwardPage).forward(req, resp);
		
	}	
}
