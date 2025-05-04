package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

public interface IReturnBookService {
	
	public List<Map<String, Object>> returnListMap();

	public List<Map<String, Object>> selectReturnedList();
	
	public boolean returnLoan(int loanNo);
	
	 // 🔍 검색 포함 페이징 처리
    Map<String, Object> getReturnListPaged(int currentPage, int pageSize, String stype, String sword);

    // ✅ 반납 완료 목록 페이징 처리
    Map<String, Object> getReturnedListPaged(int currentPage, int pageSize);
}
