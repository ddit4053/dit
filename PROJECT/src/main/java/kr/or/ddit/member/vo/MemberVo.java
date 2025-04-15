package kr.or.ddit.member.vo;

public class MemberVo {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String membershipPw;
	private String birdate;
	private String gender;
	private String memberTel;
	private String memberEmail;
	private String memberAddr;
	private String kakaoId;
	private String memberRole;  // 추가: 회원 권한(ADMIN, USER 등)
	private String regDate;     // 추가: 가입일
	
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMembershipPw() {
		return membershipPw;
	}
	public void setMembershipPw(String membershipPw) {
		this.membershipPw = membershipPw;
	}
	public String getBirdate() {
		return birdate;
	}
	public void setBirdate(String birdate) {
		this.birdate = birdate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMemberTel() {
		return memberTel;
	}
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberAddr() {
		return memberAddr;
	}
	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}
	public String getKakaoId() {
		return kakaoId;
	}
	public void setKakaoId(String kakaoId) {
		this.kakaoId = kakaoId;
	}
	public String getMemberRole() {
		return memberRole;
	}
	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	// memberIdx 대신 memberNo를 사용할 수 있도록 도움 메소드
	public int getMemberIdx() {
		return memberNo;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberNo = memberIdx;
	}
	
	// memberPhone 대신 memberTel을 사용할 수 있도록 도움 메소드
	public String getMemberPhone() {
		return memberTel;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberTel = memberPhone;
	}
}