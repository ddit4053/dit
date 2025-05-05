package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

public interface IOverdueListService {

	public List<Map<String, Object>> selectBanUserMap();
	
	boolean sendWarning(int loanNo);
	
	public Map<String, Object> getBanUserListPaged(int currentPage, int pageSize);
}
