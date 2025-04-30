package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.books.dao.BookCategoriesDaoImpl;
import kr.or.ddit.books.dao.BookReservationDaoImpl;
import kr.or.ddit.books.dao.IBookCategoriesDao;
import kr.or.ddit.books.dao.IBookReservationDao;
import kr.or.ddit.vo.BookCategoriesVo;
import kr.or.ddit.vo.BookReservationsVo;

public class BookReservationServiceImpl implements IBookReservationService {
	private static BookReservationServiceImpl instance;
	
	IBookReservationDao bookReservationDao = BookReservationDaoImpl.getInstance();
	
	private BookReservationServiceImpl() {

	}

	public static BookReservationServiceImpl getInstance() {
		if (instance == null) {
			instance = new BookReservationServiceImpl();
		}
		return instance;
	}

	@Override
	public int reservationcheck(int bookNo) {
		// TODO Auto-generated method stub
		return bookReservationDao.reservationcheck(bookNo);
	}

	@Override
	public int isAlreadyReserved(BookReservationsVo vo) {
		// TODO Auto-generated method stub
		return bookReservationDao.isAlreadyReserved(vo);
	}

	@Override
	public void reservationInsert(BookReservationsVo vo) {
		
		bookReservationDao.reservationInsert(vo);
	}

	@Override
	public List<BookReservationsVo> reserveList(BookReservationsVo vo) {
		// TODO Auto-generated method stub
		return bookReservationDao.reserveList(vo);
	}

	
	

	

}
