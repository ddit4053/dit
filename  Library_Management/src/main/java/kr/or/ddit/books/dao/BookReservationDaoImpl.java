package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BookCategoriesVo;
import kr.or.ddit.vo.BookReservationsVo;

public class BookReservationDaoImpl extends MybatisDao implements IBookReservationDao {
	private static BookReservationDaoImpl instance;

	private BookReservationDaoImpl() {

	}

	public static BookReservationDaoImpl getInstance() {
		if (instance == null) {
			instance = new BookReservationDaoImpl();
		}
		return instance;
	}

	@Override
	public int reservationcheck(int bookNo) {
		// TODO Auto-generated method stub
		return selectOne("bookrervation.reservationcheck",bookNo);
	}

	@Override
	public int isAlreadyReserved(BookReservationsVo vo) {
		// TODO Auto-generated method stub
		return selectOne("bookrervation.isAlreadyReserved",vo);
	}

	@Override
	public void reservationInsert(BookReservationsVo vo) {
		// TODO Auto-generated method stub
		insert("bookrervation.reservationInsert", vo);
	}



	

}
