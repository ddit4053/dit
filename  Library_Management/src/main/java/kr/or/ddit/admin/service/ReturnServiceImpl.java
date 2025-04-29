package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.admin.dao.IReturnDao;
import kr.or.ddit.admin.dao.ReturnDaoImpl;


public class ReturnServiceImpl implements IReturnService {
	
	private IReturnDao dao;
	
	private ReturnServiceImpl() {
		dao = ReturnDaoImpl.getDao();
	}
	
	private static IReturnService service;
	public static IReturnService getService() {
		if(service ==null) service = new ReturnServiceImpl();
		
		return service;
	}



	@Override
	public List<Map<String, Object>> returnListMap() {
		// TODO Auto-generated method stub
		return dao.returnListMap();
	}

}
