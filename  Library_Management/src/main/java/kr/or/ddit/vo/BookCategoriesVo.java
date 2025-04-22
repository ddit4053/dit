package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BookCategoriesVo {
	
	private int categoryNo;
	private String categoryName;
	private int parentId;
}
