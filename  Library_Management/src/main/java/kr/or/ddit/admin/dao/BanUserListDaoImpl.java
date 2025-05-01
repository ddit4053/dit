package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.MybatisUtil;

public class BanUserListDaoImpl implements IBanUserLIstDao {
	

	//생성자
	private BanUserListDaoImpl() {};
	
	private static IBanUserLIstDao dao;
	
	//자기자신의 객체를 생성 - 리턴
	
	public static IBanUserLIstDao getDao() {
		if(dao ==null) dao = new BanUserListDaoImpl();
		
		return dao;
	}

	@Override
	public List<Map<String, Object>> selectBanMap() {

		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list = null;
		
		try {
			list = sql.selectList("banUser.selectBanMap");
			
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
