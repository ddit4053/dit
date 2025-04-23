package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BooksVo {
	
	private int bookNo;
	private String bookTitle;
	private String isbn;
	private String pubdate;
	private String cover;
	private String bookStatus;
	private String author;
	private String publisher;
	private int categoryNo;
	private String insertDate;
}
