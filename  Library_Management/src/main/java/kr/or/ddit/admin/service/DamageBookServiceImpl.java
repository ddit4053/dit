package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.admin.dao.DamageBookDaoImpl;
import kr.or.ddit.admin.dao.IDamageBookDao;
import kr.or.ddit.vo.DamagedLostBookVo;

public class DamageBookServiceImpl implements IDamageBookService {
	
	//dao 객체 필요
	private IDamageBookDao dao;
	
	private DamageBookServiceImpl() {
		dao = DamageBookDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static IDamageBookService service;
	public static IDamageBookService getService() {
		if(service == null) service = new DamageBookServiceImpl();
		
		return service;
	}
	
	
	@Override
	public int damageInsert(DamagedLostBookVo vo) {
		// TODO Auto-generated method stub
		return dao.damageInsert(vo);
	}


	@Override
	public List<Map<String, Object>> damageBookList() {
		// TODO Auto-generated method stub
		return dao.damageBookList();
	}


	@Override
	public int updateDamage() {
		// TODO Auto-generated method stub
		return dao.updateDamage();
	}


	@Override
	public List<Map<String, Object>> getDamageListPaged(int page) {
		// TODO Auto-generated method stub
		return null;
	}

}
