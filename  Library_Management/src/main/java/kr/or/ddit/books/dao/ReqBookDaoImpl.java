package kr.or.ddit.books.dao;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BookRequestsVo;

public class ReqBookDaoImpl extends MybatisDao implements IReqBookDao {
	private static ReqBookDaoImpl instance;

	private ReqBookDaoImpl() {

	}

	public static ReqBookDaoImpl getInstance() {
		if (instance == null) {
			instance = new ReqBookDaoImpl();
		}
		return instance;
	}

	@Override
	public void reqBookInsert(BookRequestsVo vo) {
		// TODO Auto-generated method stub
		insert("bookRequset.reqBookInsert", vo);
	}

	

}
