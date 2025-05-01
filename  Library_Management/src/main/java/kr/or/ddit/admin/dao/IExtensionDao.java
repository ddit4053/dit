package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookLoanApprovalVo;

public interface IExtensionDao {
	
	//연장 요청목록 조회
	public List<Map<String, Object>> extentionListMap();
	
	//연장 승인시 연장처리 테이블 변화
	public int approveLoanExtension(BookLoanApprovalVo vo);
	
	//연장 승인시 대출관리 테이블 변화
	public int extendDueDate(BookLoanApprovalVo vo);
	
	//연장 거절
	public int rejectLoanExtension(BookLoanApprovalVo vo);

}
