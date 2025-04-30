package kr.or.ddit.books.loans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BookLoansServiceImpl;
import kr.or.ddit.books.service.IBookLoansService;
import kr.or.ddit.vo.BookLoansVo;

@WebServlet("/books/detail/loanInsert")
public class BookLoanInsertController extends HttpServlet {
	
	IBookLoansService bookLoansService = BookLoansServiceImpl.getInstance();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userNoStr = req.getParameter("userNo");
		int userNo = Integer.parseInt(userNoStr);
		
		String bookNoStr = req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookNoStr);
		
	    String status = bookLoansService.getUserStatus(userNo);
	    if ("정지".equals(status)) {
	        resp.getWriter().write("suspended");
	        return;
	    }
		
		BookLoansVo loansVo = new BookLoansVo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userNo", userNo);
		map.put("bookNo", bookNo);
		
		//대출중인지
		boolean alreadyLoaned = bookLoansService.checkAlreadyLoaned(map);
		if (alreadyLoaned) {
			resp.getWriter().write("alreadyLoaned");
			return;
		}
		
		
		int loanInsert = bookLoansService.loanInsert(map);
		
		//인서트 됐을 경우 업데이트
		String res ="";
		if(loanInsert >0) {
			bookLoansService.realbookUpdate(map);
			res = "success";
		    resp.setContentType("text/plain;charset=UTF-8");
			resp.getWriter().write(res);
		}else {
			res = "notAvailable";
		    resp.setContentType("text/plain;charset=UTF-8");
			resp.getWriter().write(res);
		}
		
	}
}
