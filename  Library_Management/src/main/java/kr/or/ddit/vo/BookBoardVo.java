package kr.or.ddit.vo;

import java.util.List;

import lombok.Data;

@Data
public class BookBoardVo {
	
	private int boardNo;
	private String title;
	private String content;
	private String writtenDate;
	private int views;
	private int userNo;
	private int bookNo;
	private int codeNo;
	private int fileGroupNum;
	private String delYn;
	
	private String writer; // 작성자명
	private int commentsCount; // 댓글 수
	private List<CommentsVo> comments; // 댓글 목록
}
