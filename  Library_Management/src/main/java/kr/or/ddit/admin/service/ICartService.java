package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;




public interface ICartService {

	public List<Map<String, Object>> cartListMap();
	
	public List<Map<String, Object>> getOverallStatsMap();

	public List<Map<String, Object>> popularBooks();
	
	public List<Map<String, Object>> categoryStats();
	
	
}
