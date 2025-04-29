package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

public interface IReturnBookService {
	
	public List<Map<String, Object>> returnListMap();
	
	public boolean returnLoan(int loanNo);

}
