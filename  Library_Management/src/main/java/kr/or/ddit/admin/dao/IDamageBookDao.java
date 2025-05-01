package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.DamagedLostBookVo;
import kr.or.ddit.vo.PagingVo;

public interface IDamageBookDao {

	public List<Map<String, Object>> damageBookList();
	
	public int damageInsert(DamagedLostBookVo vo);
	
	public int updateDamage();
	
	public int countdamageList();
	
	public List<Map<String, Object>> countdamageListPaged(PagingVo vo);
}
