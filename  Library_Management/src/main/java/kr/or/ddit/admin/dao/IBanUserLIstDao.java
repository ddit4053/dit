package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.PagingVo;

public interface IBanUserLIstDao {
	
	public List<Map<String, Object>> selectBanMap();

	public int countSelectBanList();

	public List<Map<String, Object>> countSelectBanPaged(PagingVo vo);
	
	
}
