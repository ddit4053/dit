package kr.or.ddit.returnCa.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


import kr.or.ddit.mybatis.config.MybatisUtil;
import kr.or.ddit.returnCa.vo.ReturnVo;

public class ReturnDaoImpl implements IReturnDao {
	
	private ReturnDaoImpl() {};
	
	private static IReturnDao dao;
	
	public static IReturnDao getDao() {
		if(dao ==null) dao = new ReturnDaoImpl();
		
		return dao;
	}


	@Override
	public List<ReturnVo> returnList() {
		SqlSession sql = MybatisUtil.getInstance();
		List<ReturnVo> list =null;
		
		try {
			
			list = sql.selectList("return.returnList");
			
			
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
