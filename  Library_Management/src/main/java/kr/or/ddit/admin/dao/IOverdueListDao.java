package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.Noti;
import kr.or.ddit.vo.PagingVo;

public interface IOverdueListDao {
	
	public List<Map<String, Object>> selectBanUserMap();
	
	Map<String,Object> selectLoanContact(int loanNo);
	
	int insertWarningHistory(Noti noti);
	
	public int countBanUserList();
	
	public List<Map<String, Object>> countBanUserPaged(PagingVo vo);
}