package kr.or.ddit.admin.service;

import java.util.List;

import kr.or.ddit.admin.dao.IPolicyDao;
import kr.or.ddit.admin.dao.PolicyDaoImpl;
import kr.or.ddit.vo.OverduePolicyVo;

public class PolicyServiceImpl implements IPolicyService {
	
	//dao 객체 필요
	private IPolicyDao dao;
	
	private PolicyServiceImpl() {
		dao = PolicyDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static IPolicyService service;
	public static IPolicyService getIPolicyService() {
		if(service == null) service = new PolicyServiceImpl();
		
		return service;
	}

	@Override
	public List<OverduePolicyVo> policyList() {
		// TODO Auto-generated method stub
		return dao.policyList();
	}

	@Override
	public int policyInsert(OverduePolicyVo vo) {
		// TODO Auto-generated method stub
		return dao.policyInsert(vo);
	}

	@Override
	public boolean policyApply(int policyNo) {

		
		int up1 = dao.deactivateAll();
		
		int up2 = dao.activateOne(policyNo);
		
		int up3 = dao.applyCurrentPolicy();
		
		return up1>0&&up2>0&&up3>0;
		
	}

	@Override
	public int policyDelete(int policyNo) {
		// TODO Auto-generated method stub
		return dao.policyDelete(policyNo);
	}

	@Override
	public int policyUpdate(OverduePolicyVo vo) {
		// TODO Auto-generated method stub
		return dao.policyUpdate(vo);
	}

	@Override
	public OverduePolicyVo getPolicy(int policyNo) {
		// TODO Auto-generated method stub
		return dao.selectOne(policyNo);
	}

	@Override
	public int insertAndApply(OverduePolicyVo vo) {
		
		int cnt = dao.policyInsert(vo);
	
		if("Y".equals(vo.getIsActive())) {
			
			dao.deactivateAll();
			
			dao.activateOne(vo.getPolicyNo());
		}
		
		return cnt;
	}

}
