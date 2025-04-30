package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.vo.BookCategoriesVo;
import kr.or.ddit.vo.BookReservationsVo;

public interface IBookReservationService {

	public int reservationcheck(int bookNo);

	public int isAlreadyReserved(BookReservationsVo vo); 
	
	public void reservationInsert(BookReservationsVo vo);

}
