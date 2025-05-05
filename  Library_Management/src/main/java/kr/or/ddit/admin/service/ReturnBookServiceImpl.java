package kr.or.ddit.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.admin.dao.IReturnBookDao;
import kr.or.ddit.admin.dao.ReturnBookDaoImpl;
import kr.or.ddit.vo.BookLoansVo;
import kr.or.ddit.vo.PagingVo;

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
			
			//-— 여기서 예약 처리 프로시저 호출 — 
            int bookNo = dao.getBookNoByLoanNo(loanNo);
            dao.procReserveAndLoan(bookNo);
			
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


	 /** 반납 처리 화면용 페이징 리스트 + 메타 */
	@Override
	public List<Map<String, Object>> selectReturnedList() {
		// TODO Auto-generated method stub
		return dao.selectReturnedList();
	}



	@Override
    public Map<String, Object> getReturnListPaged(int currentPage, int pageSize, String stype, String sword) {
        Map<String, Object> param = new HashMap<>();
        param.put("stype", stype);
        param.put("sword", sword);

        int totalCount = dao.countReturnListWithSearch(param);

        PagingVo paging = new PagingVo(currentPage, pageSize, totalCount);
        param.put("startRow", paging.getStartRow());
        param.put("endRow", paging.getEndRow());
    
        List<Map<String, Object>> list = dao.returnListPagedWithSearch(param);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("paging", paging);
        return result;
    }

    @Override
    public Map<String, Object> getReturnedListPaged(int currentPage, int pageSize) {
        int totalCount = dao.countReturnedList();
        PagingVo paging = new PagingVo(currentPage, pageSize, totalCount);

        List<Map<String, Object>> list = dao.returnedListPaged(paging);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("paging", paging);
        return result;
    }




}
