package kr.or.ddit.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.admin.dao.BanUserListDaoImpl;
import kr.or.ddit.admin.dao.CartDaoImpl;
import kr.or.ddit.admin.dao.IBanUserLIstDao;
import kr.or.ddit.admin.dao.ICartDao;
import kr.or.ddit.vo.PagingVo;

public class BanUserListServiceImpl implements IBanUserListService {
	

	//dao 객체 필요
	private IBanUserLIstDao dao;
	
	private BanUserListServiceImpl() {
		dao = BanUserListDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static IBanUserListService service;
	public static IBanUserListService getService() {
		if(service == null) service = new BanUserListServiceImpl();
		
		return service;
	}

	@Override
	public List<Map<String, Object>> selectBanMap() {
		// TODO Auto-generated method stub
		return dao.selectBanMap();
	}

	@Override
	public Map<String, Object> getSelectBanListPaged(int currentPage, int pageSize) {

		int totalCount = dao.countSelectBanList();
		PagingVo paging = new PagingVo(currentPage, pageSize, totalCount);
		
		List<Map<String, Object>> list = dao.countSelectBanPaged(paging);
		
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
	    result.put("paging", paging);
		
		
		return result;
	}

}
