package kr.or.ddit.loanStats.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.loanStats.vo.LoansVo;
import kr.or.ddit.mybatis.config.MybatisUtil;

public class LoansDaoImpl implements ILoansDao {
	
	//생성자
	private LoansDaoImpl() {};
	
	private static ILoansDao dao;
	
	//자기자신의 객체를 생성 - 리턴
	
	public static ILoansDao getDao() {
		if(dao ==null) dao = new LoansDaoImpl();
		
		return dao;
	}

	@Override
	public List<LoansVo> loansList() {
		SqlSession sql = MybatisUtil.getInstance();
		List<LoansVo> list = null;
		
		try {
			list = sql.selectList("loans.loansList");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		
		
		return list;
	}

}
