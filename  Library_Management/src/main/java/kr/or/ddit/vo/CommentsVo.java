package kr.or.ddit.vo;

import java.util.List;

import lombok.Data;

@Data
public class CommentsVo {
	
	private int cmNo; // 댓글 번호
	private String cmContent; // 댓글 내용
	private String cmWrittenDate; // 댓글 작성일
	private int cmNo2; // 대댓글의 부모 번호
	private int userNo; // 회원 번호
	private int boardNo; // 게시판 번호
	private String delYn; // 삭제 여부
	
	private String cmWriter; // 댓글 작성자
	private List<CommentsVo> cm2List; // 대댓글 목록
}	
