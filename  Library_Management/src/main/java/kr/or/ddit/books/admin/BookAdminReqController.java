package kr.or.ddit.books.admin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.IReqBookService;
import kr.or.ddit.books.service.ReqBookServiceImpl;
import kr.or.ddit.vo.BookRequestsVo;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class BookAdminReqController
 */
@WebServlet("/admin/books/requests")
public class BookAdminReqController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookAdminReqController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		IReqBookService reqBookService = ReqBookServiceImpl.getInstance();
		
        int page = 1;
        int pageSize = 3;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<BookRequestsVo> bookList = reqBookService.getPagedRequestBooks(page, pageSize);
        int totalCount = reqBookService.getTotalRequestCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("requestBookList", bookList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
		request.setAttribute("contentPage", "bookRequset.jsp");

		
		ServletContext ctx = request.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/admin/book/book.jsp").forward(request, response);
	}


}
