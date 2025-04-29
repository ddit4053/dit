package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.MybatisUtil;



public class ReturnDaoImpl implements IReturnDao {
	
	private ReturnDaoImpl() {};
	
	private static IReturnDao dao;
	
	public static IReturnDao getDao() {
		if(dao ==null) dao = new ReturnDaoImpl();
		
		return dao;
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
		System.out.println("반환된 리스트 크기: " + list.size());
		return list;
	}

}
