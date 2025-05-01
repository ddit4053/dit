package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;




public interface ICartDao {
	

	public List<Map<String, Object>> cartListMap();
	
	public List<Map<String, Object>> getOverallStatsMap();

}
