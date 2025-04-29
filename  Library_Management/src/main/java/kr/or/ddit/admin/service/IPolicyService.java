package kr.or.ddit.admin.service;

import java.util.List;

import kr.or.ddit.vo.OverduePolicyVo;

public interface IPolicyService {

	//모든 연체기준목록 조회
	public List<OverduePolicyVo> policyList();
	
	//연체기준목록 추가
	public int policyInsert(OverduePolicyVo vo);

	//선택한 연체기준목록 선택
	public boolean policyApply(int policyNo);

	//선택한 연체기준목록 삭제(영구 비활성화)
	public int policyDelete(int policyNo);

	//선택한 연체기준목록 수정
	public int policyUpdate(OverduePolicyVo vo);
	
	public OverduePolicyVo getPolicy(int policyNo);

	public int insertAndApply(OverduePolicyVo vo);
}
