package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.books.dao.BookCategoriesDaoImpl;
import kr.or.ddit.books.dao.IBookCategoriesDao;
import kr.or.ddit.vo.BookCategoriesVo;

public class BookCategoriesServiceImpl implements IBookCategoriesService {
	private static BookCategoriesServiceImpl instance;
	
	IBookCategoriesDao categoriesDao = BookCategoriesDaoImpl.getInstance();
	
	private BookCategoriesServiceImpl() {

	}

	public static BookCategoriesServiceImpl getInstance() {
		if (instance == null) {
			instance = new BookCategoriesServiceImpl();
		}
		return instance;
	}

	@Override
	public List<BookCategoriesVo> getCategoryListByParent(Integer parentId) {
		// TODO Auto-generated method stub
		return categoriesDao.getCategoryListByParent(parentId);
	}
	
	

	

}
