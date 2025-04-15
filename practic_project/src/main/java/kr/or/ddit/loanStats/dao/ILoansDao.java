package kr.or.ddit.loanStats.dao;

import java.util.List;

import kr.or.ddit.loanStats.vo.LoansVo;

public interface ILoansDao {
	
	public List<LoansVo> loansList();

}
