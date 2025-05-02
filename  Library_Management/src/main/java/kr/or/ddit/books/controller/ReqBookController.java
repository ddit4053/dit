package kr.or.ddit.books.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.books.service.IReqBookService;
import kr.or.ddit.books.service.ReqBookServiceImpl;
import kr.or.ddit.vo.BookRequestsVo;

@WebServlet("/books/bookRequest")
public class ReqBookController extends HttpServlet{
	IReqBookService reqBookService = ReqBookServiceImpl.getInstance();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		      
	    String reqBookTitle = req.getParameter("title");   
	    String reqBookAuthor = req.getParameter("author");   
	    String reqBookPublisher = req.getParameter("publisher");
	    String reqBookComment = req.getParameter("notes"); 
	    String reqIsbn = req.getParameter("isbn");         
	    int userNo = (int) session.getAttribute("userNo");           
	    
	    BookRequestsVo vo = new BookRequestsVo();
	    
	    vo.setReqBookTitle(reqBookTitle);
	    vo.setReqBookAuthor(reqBookAuthor);
	    vo.setReqBookPublisher(reqBookPublisher);
	    vo.setReqBookComment(reqBookComment);
	    vo.setReqIsbn(reqIsbn);
	    vo.setUserNo(userNo);
	    
	    
	    int reqBook = reqBookService.reqBookInsert(vo);
	    
	    if(reqBook >0) {
	    	req.setAttribute("requestSuccess", true);
	    }
	    else {
	    	req.setAttribute("requestSuccess", false);
	    }
	    req.setAttribute("contentPage", "search.jsp");
	    
	    ServletContext ctx = req.getServletContext();
	    ctx.getRequestDispatcher("/WEB-INF/view/users/book_search/books.jsp").forward(req, resp);
	}
}
