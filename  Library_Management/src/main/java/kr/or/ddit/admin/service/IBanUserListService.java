package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

public interface IBanUserListService {
	
	public List<Map<String, Object>> selectBanMap();

	public Map<String, Object> getSelectBanListPaged(int currentPage, int pageSize);
	
}
