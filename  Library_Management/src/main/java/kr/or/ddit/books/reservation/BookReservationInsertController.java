package kr.or.ddit.books.reservation;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BookReservationServiceImpl;
import kr.or.ddit.books.service.IBookReservationService;
import kr.or.ddit.vo.BookReservationsVo;

@WebServlet("/books/detail/reserInsert")
public class BookReservationInsertController extends HttpServlet {
	
	IBookReservationService bookReservationService = BookReservationServiceImpl.getInstance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			int userNo = Integer.parseInt(req.getParameter("userNo"));		
			
			int bookNo = Integer.parseInt(req.getParameter("bookNo"));
		
			BookReservationsVo vo = new BookReservationsVo();
			vo.setBookNo(bookNo);
			vo.setUserNo(userNo);
			
			int isAlreadyReserved = bookReservationService.isAlreadyReserved(vo);
			
			String res ="";
			if(isAlreadyReserved == 0) {
				bookReservationService.reservationInsert(vo);
				res="reserveInsert";
				resp.setContentType("text/plain;charset=UTF-8");
				resp.getWriter().write(res);
			}else {
				res="alreadyreserve";
				resp.setContentType("text/plain;charset=UTF-8");
				resp.getWriter().write(res);
			}
	}
}
