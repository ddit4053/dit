package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.vo.MemberVo;

public class MemberServiceImpl implements IMemberService {
	
    private static MemberServiceImpl instance;

    private MemberServiceImpl() {
    }

    public static MemberServiceImpl getInstance() {
        if (instance == null) {
            instance = new MemberServiceImpl();
        }
        return instance;
    }
    
    IMemberDao memberDao = MemberDaoImpl.getInstance();

    @Override
    public MemberVo login(MemberVo member) {
        return memberDao.login(member);
    }

    @Override
    public MemberVo findMemberByKakaoId(String kakaoId) {
        // DAO에 카카오 ID로 회원 조회 메서드 호출
        return memberDao.findMemberByKakaoId(kakaoId);
    }

    @Override
    public boolean registerKakaoMember(MemberVo member) {
        // DAO에 카카오 회원 등록 메서드 호출
        return memberDao.registerKakaoMember(member);
    }

    @Override
    public List<MemberVo> getMemberList() {
        return memberDao.getMemberList();
    }
    
    @Override
    public boolean registerMember(MemberVo member) {
        // 일반 회원 등록
        return memberDao.registerMember(member);
    }
    
    @Override
    public boolean isIdAvailable(String memberId) {
        // 아이디 중복 확인 (사용 가능하면 true)
        return memberDao.isIdAvailable(memberId);
    }
    
    @Override
    public String findMemberIdByNameAndEmail(String memberName, String memberEmail) {
        // 이름과 이메일로 아이디 찾기
        return memberDao.findMemberIdByNameAndEmail(memberName, memberEmail);
    }
    
    @Override
    public MemberVo findMemberByIdAndEmail(String memberId, String memberEmail) {
        // 아이디와 이메일로 회원 찾기
        return memberDao.findMemberByIdAndEmail(memberId, memberEmail);
    }
    
    @Override
    public boolean savePasswordResetToken(int memberNo, String token) {
        // 비밀번호 재설정 토큰 저장
        return memberDao.savePasswordResetToken(memberNo, token);
    }
    
    @Override
    public boolean resetPasswordWithToken(int memberNo, String token, String newPassword) {
        // 토큰으로 비밀번호 재설정
        return memberDao.resetPasswordWithToken(memberNo, token, newPassword);
    }
    @Override
    public boolean checkMemberByNameAndEmail(String memberName, String memberEmail) {
        return memberDao.checkMemberByNameAndEmail(memberName, memberEmail);
    }

    @Override
    public boolean saveEmailVerificationCode(String email, String code, String purpose) {
        return memberDao.saveEmailVerificationCode(email, code, purpose);
    }

    @Override
    public boolean verifyEmailCode(String email, String code, String purpose) {
        return memberDao.verifyEmailCode(email, code, purpose);
    }

    @Override
    public String findMemberIdByEmail(String email) {
        return memberDao.findMemberIdByEmail(email);
    }


	
    
}