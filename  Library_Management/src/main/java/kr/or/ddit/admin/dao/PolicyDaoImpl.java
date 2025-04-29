package kr.or.ddit.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.MybatisUtil;
import kr.or.ddit.vo.OverduePolicyVo;

public class PolicyDaoImpl implements IPolicyDao {
	
	//생성자
	private PolicyDaoImpl() {};
	private static IPolicyDao dao;
	
	//자기자신의 객체를 생성 - 리턴
	public static IPolicyDao getDao() {
		if(dao == null) dao = new PolicyDaoImpl();
		
		return dao;
	}

	@Override
	public List<OverduePolicyVo> policyList() {
		
		SqlSession sql = kr.or.ddit.util.MybatisUtil.getInstance();
		List<OverduePolicyVo> list = null;
		
		try {
			list = sql.selectList("policy.policyList");
			
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
	public int policyInsert(OverduePolicyVo vo) {

		SqlSession sql = MybatisUtil.getInstance();
		int list = 0;
		
		try {
			list = sql.insert("policy.policyInsert", vo);
			
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
	public int deactivateAll() {
		
		SqlSession sql = MybatisUtil.getInstance();
		int list = 0;
		
		try {
			list = sql.update("policy.deactivateAll");
			
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
	public int activateOne(int policyNo) {

		SqlSession sql = MybatisUtil.getInstance();
		int one = 0;
		
		try {
			one = sql.update("policy.activateOne", policyNo);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		
		return one;
	}

	@Override
	public int policyDelete(int policyNo) {

		SqlSession sql = MybatisUtil.getInstance();
		int del = 0;
		
		try {
			del = sql.update("policy.policyDelete", policyNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		
		return del;
	}

	@Override
	public int policyUpdate(OverduePolicyVo vo) {

		SqlSession sql = MybatisUtil.getInstance();
		int del = 0;
		
		try {
			del = sql.update("policy.policyUpdate", vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql != null) {
				sql.commit();
				sql.close();
			}
		}
		
		return del;
	}

	@Override
	public OverduePolicyVo selectOne(int policyNo) {
		
		SqlSession sql = MybatisUtil.getInstance();
		OverduePolicyVo list = null;
		
		try {
			list = sql.selectOne("policy.selectOne", policyNo);
			
		}catch (Exception e) {
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
