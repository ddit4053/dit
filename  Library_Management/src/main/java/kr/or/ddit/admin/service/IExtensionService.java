package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookLoanApprovalVo;

public interface IExtensionService {
		//연장 요청목록 조회
		public List<Map<String, Object>> extensionListMap();
		
		//연장 승인시 연장처리 테이블 변화
		public boolean approveExtension(BookLoanApprovalVo vo);
				
		
		//연장 거절
		public int rejectLoanExtension(BookLoanApprovalVo vo);

}
