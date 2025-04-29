package kr.or.ddit.books.service;

import java.util.Map;

import kr.or.ddit.books.dao.BookLoansDaoImpl;
import kr.or.ddit.books.dao.IBookLoansDao;
import kr.or.ddit.vo.BookLoansVo;

public class BookLoansServiceImpl implements IBookLoansService {
	private static BookLoansServiceImpl instance;

	IBookLoansDao bookLoansDao = BookLoansDaoImpl.getInstance();
	private BookLoansServiceImpl() {

	}

	public static BookLoansServiceImpl getInstance() {
		if (instance == null) {
			instance = new BookLoansServiceImpl();
		}
		return instance;
	}

	@Override
	public int loanInsert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bookLoansDao.loanInsert(map);
	}

	@Override
	public void realbookUpdate(Map<String, Object> map) {
		bookLoansDao.realbookUpdate(map);
		
	}

	

}
