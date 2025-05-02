package kr.or.ddit.books.service;

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
	public void reqBookInsert(BookRequestsVo vo) {
		
		reqBookDao.reqBookInsert(vo);
	}

	

}
