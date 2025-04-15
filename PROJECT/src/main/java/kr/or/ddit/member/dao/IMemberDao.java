package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.member.vo.MemberVo;

public interface IMemberDao {
    
    /**
     * 로그인 처리를 위한 회원 조회
     * @param member 로그인할 회원 정보 (아이디, 비밀번호)
     * @return 로그인 성공 시 회원 정보, 실패 시 null
     */
    public MemberVo login(MemberVo member);
    
    /**
     * 카카오 ID로 회원 찾기
     * @param kakaoId 카카오 ID
     * @return 회원 정보, 존재하지 않으면 null
     */
    public MemberVo findMemberByKakaoId(String kakaoId);
    
    /**
     * 카카오 회원 등록
     * @param member 등록할 회원 정보
     * @return 등록 성공 여부
     */
    public boolean registerKakaoMember(MemberVo member);
    
    /**
     * 회원 목록 조회
     * @return 회원 목록
     */
    public List<MemberVo> getMemberList();
    
    /**
     * 일반 회원 등록
     * @param member 등록할 회원 정보
     * @return 등록 성공 여부
     */
    public boolean registerMember(MemberVo member);
    
    /**
     * 아이디 중복 확인
     * @param memberId 확인할 아이디
     * @return 사용 가능한 아이디인지 여부 (true: 사용 가능, false: 이미 사용 중)
     */
    public boolean isIdAvailable(String memberId);
    
    /**
     * 이름과 이메일로 회원 아이디 찾기
     * @param memberName 회원 이름
     * @param memberEmail 회원 이메일
     * @return 찾은 아이디, 없으면 null
     */
    public String findMemberIdByNameAndEmail(String memberName, String memberEmail);
    
    /**
     * 아이디와 이메일로 회원 정보 찾기
     * @param memberId 회원 아이디
     * @param memberEmail 회원 이메일
     * @return 회원 정보, 없으면 null
     */
    public MemberVo findMemberByIdAndEmail(String memberId, String memberEmail);
    
    /**
     * 비밀번호 재설정 토큰 저장
     * @param memberIdx 회원 번호
     * @param token 비밀번호 재설정 토큰
     * @return 저장 성공 여부
     */
    public boolean savePasswordResetToken(int memberNo, String token);
    
    /**
     * 토큰을 이용한 비밀번호 재설정
     * @param memberIdx 회원 번호
     * @param token 비밀번호 재설정 토큰
     * @param newPassword 새 비밀번호
     * @return 재설정 성공 여부
     */
    public boolean resetPasswordWithToken(int memberNo, String token, String newPassword);
    public boolean checkMemberByNameAndEmail(String memberName, String memberEmail);
    public boolean saveEmailVerificationCode(String email, String code, String purpose);
    public boolean verifyEmailCode(String email, String code, String purpose);
    public String findMemberIdByEmail(String email);
    
}