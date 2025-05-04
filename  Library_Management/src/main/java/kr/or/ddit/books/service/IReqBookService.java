package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.vo.BookRequestsVo;

public interface IReqBookService {

	public int reqBookInsert(BookRequestsVo vo);

	public List<BookRequestsVo> getPagedRequestBooks(int page, int pageSize);

	public int getTotalRequestCount();

	public int updateSuccess(String reqBookNo);

}
