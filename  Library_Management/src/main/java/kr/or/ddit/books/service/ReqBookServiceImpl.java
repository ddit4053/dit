package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.books.dao.IReqBookDao;
import kr.or.ddit.books.dao.ReqBookDaoImpl;
import kr.or.ddit.vo.BookRequestsVo;

public class ReqBookServiceImpl implements IReqBookService {
	IReqBookDao reqBookDao = ReqBookDaoImpl.getInstance();
	private static ReqBookServiceImpl instance;

	private ReqBookServiceImpl() {

	}

	public static ReqBookServiceImpl getInstance() {
		if (instance == null) {
			instance = new ReqBookServiceImpl();
		}
		return instance;
	}

	@Override
	public int reqBookInsert(BookRequestsVo vo) {
		
		return reqBookDao.reqBookInsert(vo);
	}

	@Override
	public List<BookRequestsVo> getPagedRequestBooks(int page, int pageSize) {
		// TODO Auto-generated method stub
        int offset = (page - 1) * pageSize;
        return reqBookDao.getPagedRequestBooks(offset, pageSize);
	}

	@Override
	public int getTotalRequestCount() {
		// TODO Auto-generated method stub
		return reqBookDao.getTotalRequestCount();
	}

	@Override
	public int updateSuccess(String reqBookNo) {
		// TODO Auto-generated method stub
		return reqBookDao.updateSuccess(reqBookNo);
	}

	

}
