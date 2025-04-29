package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.admin.dao.IReturnBookDao;
import kr.or.ddit.admin.dao.ReturnBookDaoImpl;
import kr.or.ddit.vo.BookLoansVo;

public class ReturnBookServiceImpl implements IReturnBookService {
	
	//dao 객체 필요
	private IReturnBookDao dao;
	
	private ReturnBookServiceImpl() {
		dao = ReturnBookDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static IReturnBookService service;
	public static IReturnBookService getService() {
		if(service == null) service = new ReturnBookServiceImpl();
				
		return service;
	}
	
	
	
	@Override 
	public boolean returnLoan(int loanNo) {
		boolean success = false;
		
		try {
			BookLoansVo vo = new BookLoansVo();
			vo.setLoanNo(loanNo);

			// 1) 반납일 설정
			dao.bookLoanReturn(vo);
			
			 // 2) 책 상태 변경
			dao.updateReturnBook(vo);
			
			// 3) 지연반납자 상태변경
			
			dao.restoreDelayed();
			
			// 4) 정지 해제일 설정
			dao.banReleaseDateOnReturn(vo);
			
		

			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return success;
	}



	@Override
	public List<Map<String, Object>> returnListMap() {
		// TODO Auto-generated method stub
		return dao.returnListMap();
	}

}
