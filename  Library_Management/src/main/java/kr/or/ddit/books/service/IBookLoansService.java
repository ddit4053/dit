package kr.or.ddit.books.service;

import java.util.Map;

import kr.or.ddit.vo.BookLoansVo;

public interface IBookLoansService {

	public int loanInsert(Map<String, Object> map);

	public void realbookUpdate(Map<String, Object> map);

}
