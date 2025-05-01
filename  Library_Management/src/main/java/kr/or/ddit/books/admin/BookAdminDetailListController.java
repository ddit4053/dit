package kr.or.ddit.books.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.RealBookVo;

@WebServlet("/admin/books/detailList")
public class BookAdminDetailListController extends HttpServlet {
	IBooksService booksService = BooksServiceImp.getInsatance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int bookNo = Integer.parseInt(req.getParameter("bookNo"));
		
		List<Map<String, Object>> realbookList= booksService.realBookList(bookNo);
		
		req.setAttribute("contentPage", "booksDetail.jsp");
		req.setAttribute("realbookList", realbookList);
		
		ServletContext ctx = req.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/admin/book/book.jsp").forward(req, resp);
	}
}
