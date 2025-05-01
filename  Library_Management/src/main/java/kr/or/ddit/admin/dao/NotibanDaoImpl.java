package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.MybatisUtil;
import kr.or.ddit.vo.Noti;


public class NotibanDaoImpl implements INotiBanDao {
	
	
	//생성자
	private NotibanDaoImpl() {};
	private static INotiBanDao dao;
	
	//자기자신의 객체를  생성 - 리턴
	public static INotiBanDao getDao() {
		if(dao == null) dao = new NotibanDaoImpl();
		
		return dao;
	}
	
	@Override
	public int countDueNotifications() {
		SqlSession sql = MybatisUtil.getInstance();
		int cnt = 0;
		try {
			cnt =sql.selectOne("notiBan.countDueNotifications");
			
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
	public int countOverdueNotifications() {

		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.selectOne("notiBan.countOverdueNotifications");
			
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
	public List<Map<String, Object>> listDueNotifications() {
		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list =null;
		
		try {
			list = sql.selectList("notiBan.listDueNotifications");
			
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
	public List<Map<String, Object>> listOverdueNotifications() {
		SqlSession sql = MybatisUtil.getInstance();
		List<Map<String, Object>> list =null;
		
		try {
			list = sql.selectList("notiBan.listOverdueNotifications");
			
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
	public int insertWarningHistory(Noti noti) {
		
		SqlSession sql = MybatisUtil.getInstance();
		int cnt =0;
		
		try {
			cnt = sql.insert("notiBan.insertWarningHistory", noti);
			
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

}
