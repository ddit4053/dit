package kr.or.ddit.loanStats.service;

import java.util.List;

import kr.or.ddit.loanStats.dao.ILoansDao;
import kr.or.ddit.loanStats.dao.LoansDaoImpl;
import kr.or.ddit.loanStats.vo.LoansVo;

public class LoansServiceImpl implements ILoansService {
	
	//dao 객체 필요
	private ILoansDao dao;
	
	private LoansServiceImpl() {
		dao = LoansDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static ILoansService service;
	public static ILoansService getService() {
		if(service == null) service = new LoansServiceImpl();
		
		return service;
	}

	@Override
	public List<LoansVo> loansList() {
		// TODO Auto-generated method stub
		return dao.loansList();
	}

}
