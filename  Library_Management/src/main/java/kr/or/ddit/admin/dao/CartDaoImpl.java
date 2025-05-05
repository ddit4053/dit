package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.MybatisUtil;




public class CartDaoImpl implements ICartDao {
	
	//생성자
	private CartDaoImpl() {};
	
	private static ICartDao dao;
	
	//자기자신의 객체를 생성 - 리턴
	
	public static ICartDao getDao() {
		if(dao ==null) dao = new CartDaoImpl();
		
		return dao;
	}

	

	@Override
	public List<Map<String, Object>> cartListMap() {
		
		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list = null;
		
		try {
			list = sql.selectList("cart.cartListMap");
			
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

	@Override
	public List<Map<String, Object>> getOverallStatsMap() {
		
		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list = null;
		
		try {
			list = sql.selectList("cart.getOverallStatsMap");
			
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



	@Override
	public List<Map<String, Object>> popularBooks() {

		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list = null;
		
		try {
			list = sql.selectList("cart.popularBooks");
			
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



	@Override
	public List<Map<String, Object>> categoryStats() {

		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list = null;
		
		try {
			list = sql.selectList("cart.categoryStats");
			
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



	@Override
	public List<Map<String, Object>> loansUserStats() {

		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list = null;
		
		try {
			list = sql.selectList("cart.loansUserStats");
			
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
