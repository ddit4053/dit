package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.vo.BookRequestsVo;

public interface IReqBookDao {

	public int reqBookInsert(BookRequestsVo vo);

	public List<BookRequestsVo> getPagedRequestBooks(int offset, int pageSize);

	public int getTotalRequestCount();

}
