package kr.or.ddit.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.member.util.MybatisDao;
import kr.or.ddit.member.vo.MemberVo;

public class MemberDaoImpl extends MybatisDao implements IMemberDao {
    private static MemberDaoImpl instance;

    private MemberDaoImpl() {
    }

    public static MemberDaoImpl getInstance() {
        if (instance == null) {
            instance = new MemberDaoImpl();
        }
        return instance;
    }

    @Override
    public MemberVo login(MemberVo member) {
        return selectOne("member.login", member);
    }
    
    @Override
    public MemberVo findMemberByKakaoId(String kakaoId) {
        return selectOne("member.findByKakaoId", kakaoId);
    }
    
    @Override
    public boolean registerKakaoMember(MemberVo member) {
        // insert 대신 update 메서드 사용
        int result = update("member.registerKakaoMember", member);
        return result > 0;
    }

    @Override
    public List<MemberVo> getMemberList() {
        return selectList("member.getMemberList");
    }
    
    @Override
    public boolean registerMember(MemberVo member) {
        int result = update("member.registerMember", member);
        return result > 0;
    }
    
    @Override
    public boolean isIdAvailable(String memberId) {
        Integer count = selectOne("member.countMemberById", memberId);
        return count != null && count == 0; // count가 0이면 사용 가능
    }
    
    @Override
    public String findMemberIdByNameAndEmail(String memberName, String memberEmail) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberName", memberName);
        params.put("memberEmail", memberEmail);
        
        return selectOne("member.findMemberIdByNameAndEmail", params);
    }
    
    @Override
    public MemberVo findMemberByIdAndEmail(String memberId, String memberEmail) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("memberEmail", memberEmail);
        
        return selectOne("member.findMemberByIdAndEmail", params);
    }
    
    @Override
    public boolean savePasswordResetToken(int memberNo, String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberNo", memberNo);
        params.put("token", token);
        params.put("expireDate", new java.sql.Timestamp(System.currentTimeMillis() + (24 * 60 * 60 * 1000))); // 24시간 유효
        
        int result = update("member.savePasswordResetToken", params);
        return result > 0;
    }
    
    @Override
    public boolean resetPasswordWithToken(int memberNo, String token, String newPassword) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberNo", memberNo);
        params.put("token", token);
        params.put("newPassword", newPassword);
        
        int result = update("member.resetPasswordWithToken", params);
        return result > 0;
    }
 // MemberDaoImpl.java에 추가
    @Override
    public boolean checkMemberByNameAndEmail(String memberName, String memberEmail) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberName", memberName);
        params.put("memberEmail", memberEmail);
        
        Integer count = selectOne("member.countMemberByNameAndEmail", params);
        return count != null && count > 0;
    }

    @Override
    public boolean saveEmailVerificationCode(String email, String code, String purpose) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("code", code);
        params.put("purpose", purpose);
        params.put("expireDate", new java.sql.Timestamp(System.currentTimeMillis() + (10 * 60 * 1000))); // 10분 유효
        
        int result = update("member.saveEmailVerificationCode", params);
        return result > 0;
    }

    @Override
    public boolean verifyEmailCode(String email, String code, String purpose) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("code", code);
        params.put("purpose", purpose);
        
        Integer count = selectOne("member.verifyEmailCode", params);
        
        if (count != null && count > 0) {
            // 인증 성공 시 인증 상태 업데이트
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("email", email);
            updateParams.put("code", code);
            updateParams.put("purpose", purpose);
            update("member.updateEmailVerificationStatus", updateParams);
            return true;
        }
        return false;
    }

    @Override
    public String findMemberIdByEmail(String email) {
        return selectOne("member.findMemberIdByEmail", email);
    }

    
}