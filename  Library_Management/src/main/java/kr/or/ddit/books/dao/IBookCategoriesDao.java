package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.vo.BookCategoriesVo;

public interface IBookCategoriesDao {

	List<BookCategoriesVo> getCategoryListByParent(Integer parentId);
	
}
