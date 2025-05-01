package kr.or.ddit.books.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/books/*")
public class BooksController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo(); // /search, /new, /favor /recommend  등을 가져옴
		String contentPage = null;
		String forwardPage = null;
		
		// pathInfo에 따라 contentPage와 forwardPage 설정
		if(pathInfo == null || pathInfo.equals("")) {
			resp.sendRedirect(req.getContextPath() + "/books/search");
			return;
		}
		
		switch (pathInfo) {
			
		case "/search":
            contentPage = "/WEB-INF/view/users/book_search/search.jsp";
            forwardPage = "/WEB-INF/view/users/book_search/books.jsp";
            break;
            
		case "/search/searchCategory":
			contentPage = "/WEB-INF/view/users/book_search/category.jsp";
			forwardPage = "/WEB-INF/view/users/book_search/books.jsp";
			break;
            
        case "/new":
            contentPage = "/WEB-INF/view/users/book_search/new.jsp";
            forwardPage = "/WEB-INF/view/users/book_search/books.jsp";
            break;
            
        case "/favor":
            contentPage = "/WEB-INF/view/users/book_search/favor.jsp";
            forwardPage = "/WEB-INF/view/users/book_search/books.jsp";
            break;
            
        case "/recommend":
        	contentPage = "/WEB-INF/view/users/book_search/recommend.jsp";
        	forwardPage = "/WEB-INF/view/users/book_search/books.jsp";
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
