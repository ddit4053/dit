package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.vo.BookCategoriesVo;
import kr.or.ddit.vo.BookReservationsVo;

public interface IBookReservationDao {

	public int reservationcheck(int bookNo);
	
	public int isAlreadyReserved(BookReservationsVo vo); 
	
	public void reservationInsert(BookReservationsVo vo);

	public List<BookReservationsVo> reserveList(BookReservationsVo vo);

	
}
