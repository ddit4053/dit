package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.MybatisUtil;
import kr.or.ddit.util.MybatisUtil;
import kr.or.ddit.vo.BookLoansVo;

public class ReturnBookDaoImpl implements IReturnBookDao {
	
	//생성자
	private ReturnBookDaoImpl() {};
	private static IReturnBookDao dao;
	
	// 자기자신의 객체를 생성 - 리턴
	
	public static IReturnBookDao getDao() {
		if(dao == null) dao = new ReturnBookDaoImpl();
		
		return dao;
	}
	
	
	
	@Override
	public int bookLoanReturn(BookLoansVo loanNo) {
		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.update("return.bookLoanReturn", loanNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		return cnt;
	}

	@Override
	public int updateReturnBook(BookLoansVo loanNo) {

		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.update("return.updateReturnBook", loanNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		return cnt;
	}

	@Override
	public int banReleaseDateOnReturn(BookLoansVo loanNo) {

		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.update("return.banReleaseDateOnReturn", loanNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		return cnt;
	}

	@Override
	public int restoreDelayed() {

		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.update("return.restoreDelayed");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		return cnt;
	}

	@Override
	public int restoreBanned() {

		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.update("return.restoreBanned");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		return cnt;
	}



	@Override
	public List<Map<String, Object>> returnListMap() {

		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list =null;
		
		try {
			list = sql.selectList("return.returnListMap");
			
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
