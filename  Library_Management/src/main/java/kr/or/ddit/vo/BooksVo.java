package kr.or.ddit.vo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BooksVo {
	
	private int bookNo;
	
	@SerializedName("title")
	private String bookTitle;
	
	private String isbn;
	
	@SerializedName("pubDate")
	private String pubdate;
	
	private String cover;
	private String bookStatus;
	private String author;
	private String publisher;
	
	@SerializedName("categoryId")
	private int categoryNo;
	private String insertDate;
}
