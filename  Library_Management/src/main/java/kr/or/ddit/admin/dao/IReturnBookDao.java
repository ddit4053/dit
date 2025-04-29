package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookLoansVo;

public interface IReturnBookDao {
	
	// 대출기록 리스트
	public List<Map<String, Object>> returnListMap();
	
	//1) 정상 반납시  Book_Loans.return_date
	public int bookLoanReturn(BookLoansVo loanNo);
	
	//2)  도서를 반납시 도서의 상태변화 
	public int updateReturnBook(BookLoansVo loanNo);
	
	//3) 연체자 반납 시 ban_users.release_date 설정
	public int banReleaseDateOnReturn(BookLoansVo loanNo);
	
	//반납 후 사용자 상태 복구
	//4) 지연 반납자(경고) → 반납 완료 시 정상 복귀
	public int restoreDelayed();
	
	//5) ban_users 테이블 기준 정지자 자동 복귀
	public int restoreBanned();
	
}
