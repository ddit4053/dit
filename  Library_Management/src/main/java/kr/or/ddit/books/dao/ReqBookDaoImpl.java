package kr.or.ddit.books.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public int reqBookInsert(BookRequestsVo vo) {
		return insert("bookRequset.reqBookInsert", vo);
	}

	@Override
	public List<BookRequestsVo> getPagedRequestBooks(int offset, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", offset);
		map.put("pageSize", pageSize);
		
		return selectList("bookRequset.getPagedRequestBooks", map);
	}

	@Override
	public int getTotalRequestCount() {
		// TODO Auto-generated method stub
		return selectOne("bookRequset.getTotalRequestCount");
	}

	@Override
	public int updateSuccess(String reqBookNo) {
		// TODO Auto-generated method stub
		return update("bookRequset.updateSuccess", reqBookNo);
	}

	@Override
	public int updateReject(String reqBookNo) {
		// TODO Auto-generated method stub
		return update("bookRequset.updateReject", reqBookNo);
	}

	

}
