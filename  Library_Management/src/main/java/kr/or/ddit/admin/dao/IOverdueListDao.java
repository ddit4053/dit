package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.Noti;

public interface IOverdueListDao {
	
	public List<Map<String, Object>> selectBanUserMap();
	
	Map<String,Object> selectLoanContact(int loanNo);
	
	int insertWarningHistory(Noti noti);
}