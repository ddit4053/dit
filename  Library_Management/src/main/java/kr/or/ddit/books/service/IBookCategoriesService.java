package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.vo.BookCategoriesVo;

public interface IBookCategoriesService {

	List<BookCategoriesVo> getCategoryListByParent(Integer parentId);
	
	

}
