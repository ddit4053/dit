package kr.or.ddit.books.reservation;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BookReservationServiceImpl;
import kr.or.ddit.books.service.IBookReservationService;
import kr.or.ddit.vo.BookReservationsVo;

@WebServlet("/books/detail/reserCheck")
public class BookReservationCheckController extends HttpServlet {
	IBookReservationService bookReservationService = BookReservationServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userNo = Integer.parseInt(req.getParameter("userNo"));
		
		String bookNoStr =	req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookNoStr);
		
		BookReservationsVo vo = new BookReservationsVo();
		vo.setBookNo(bookNo);
		vo.setUserNo(userNo);
		
		int reservationcheck = bookReservationService.reservationcheck(bookNo);
		int isAlreadyReserved = bookReservationService.isAlreadyReserved(vo);
		
		if(reservationcheck == 0) {
			String res = "reservation";
			if(isAlreadyReserved!=0) {
				res = "alreadyreserve";
			}
			resp.setContentType("text/plain;charset=UTF-8");
			resp.getWriter().write(res);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userNo = Integer.parseInt(req.getParameter("userNo"));
		
		String bookNoStr =	req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookNoStr);
		
		BookReservationsVo vo = new BookReservationsVo();
		vo.setBookNo(bookNo);
		vo.setUserNo(userNo);
		
		Gson gson = new GsonBuilder().create();
		
		List<BookReservationsVo> resrveList = bookReservationService.reserveList(vo);
		
		
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(gson.toJson(resrveList));
	}
}
