package kr.or.ddit.books.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BooksVo;

@WebServlet("/books/new")
public class NewBookListController extends HttpServlet{
	IBooksService booksService = BooksServiceImp.getInsatance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
        int pageSize = 5; // 한 페이지에 표시할 도서 수

        // 전체 신착 도서 개수
        int totalCount = 30;

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 페이징된 신착 도서 목록 가져오기
        List<BooksVo> pagedBooks = booksService.getNewBooksByPage(page, pageSize);

        // JSP로 전달할 데이터 설정
        req.setAttribute("newBookList", pagedBooks);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);
		req.setAttribute("contentPage", "newBooks.jsp");
        // JSP로 포워드
		ServletContext ctx = req.getServletContext();
		ctx.getRequestDispatcher("/WEB-INF/view/users/book_search/books.jsp").forward(req, resp);

	}
}
