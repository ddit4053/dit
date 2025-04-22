package kr.or.ddit.vo;

import lombok.Data;

@Data
public class UsertsVo {
	private int userNo;
	private String userId;
	private String password;
	private String name;
	private String birth;
	private String email;
	private String phone;
	private String address;
	private String role;
	private String createdDate;
	private String kakaoId;
	private String gender;
	private String status;
}
