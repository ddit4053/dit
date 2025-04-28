package kr.or.ddit.books.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BooksVo;
import kr.or.ddit.vo.ReviewsVo;

public class BooksDaoImpl extends MybatisDao implements IBooksDao {

	private static BooksDaoImpl insatance;

	private BooksDaoImpl() {

	}

	public static BooksDaoImpl getInsatance() {
		if (insatance == null) {
			insatance = new BooksDaoImpl();
		}
		return insatance;
	}
	
	
	@Override
	public int insertBooks(BooksVo vo) {
		// TODO Auto-generated method stub
		return update("books.insertBooks", vo);
	}

	@Override
	public List<BooksVo> listBooks() {
		// TODO Auto-generated method stub
		return selectList("books.listBooks");
	}

	@Override
	public List<BooksVo> searchBookList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selectList("books.searchBookList",map);
	}


	@Override
	public BooksVo booksDetail(BooksVo bookvo) {
		// TODO Auto-generated method stub
		return selectOne("books.booksDetail",bookvo);
	}

	@Override
	public List<BooksVo> mainSearchBookList(Map<String, Object> mapMain) {
		// TODO Auto-generated method stub
		return selectList("books.mainSearchBookList",mapMain);
	}

	@Override
	public int countSearchBook(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selectOne("books.countSearchBook",map);
	}

	@Override
	public int countMainSearchBook(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selectOne("books.countMainSearchBook", map);
	}

	@Override
	public List<BooksVo> bookIsbnList() {
		// TODO Auto-generated method stub
		return selectList("books.bookIsbnList");
	}


	
}
