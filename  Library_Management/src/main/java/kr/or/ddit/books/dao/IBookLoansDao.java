package kr.or.ddit.books.dao;

import java.util.Map;

import kr.or.ddit.vo.BookLoansVo;

public interface IBookLoansDao {

	public int loanInsert(Map<String, Object> map);

	public void realbookUpdate(Map<String, Object> map);

}
