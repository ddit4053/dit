package kr.or.ddit.books.dao;

import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BookLoansVo;

public class BookLoansDaoImpl extends MybatisDao implements IBookLoansDao {
	private static BookLoansDaoImpl instance;

	private BookLoansDaoImpl() {

	}

	public static BookLoansDaoImpl getInstance() {
		if (instance == null) {
			instance = new BookLoansDaoImpl();
		}
		return instance;
	}

	@Override
	public int loanInsert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return insert("bookLoan.loanInsert", map);
	}

	@Override
	public void realbookUpdate(Map<String, Object> map) {
		update("bookLoan.realbookUpdate", map);
		
	}

	

}
