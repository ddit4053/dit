package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BookCategoriesVo;

public class BookCategoriesDaoImpl extends MybatisDao implements IBookCategoriesDao {
	private static BookCategoriesDaoImpl instance;

	private BookCategoriesDaoImpl() {

	}

	public static BookCategoriesDaoImpl getInstance() {
		if (instance == null) {
			instance = new BookCategoriesDaoImpl();
		}
		return instance;
	}

	@Override
	public List<BookCategoriesVo> getCategoryListByParent(Integer parentId) {
		// TODO Auto-generated method stub
		return selectList("bookCategorie.getCategoryListByParent",parentId);
	}

	

}
