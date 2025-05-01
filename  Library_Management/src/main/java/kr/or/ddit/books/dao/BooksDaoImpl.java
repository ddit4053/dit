package kr.or.ddit.books.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BooksVo;
import kr.or.ddit.vo.RealBookVo;
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
	public List<BooksVo> listBooks(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selectList("books.listBooks",map);
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

	@Override
	public List<Map<String, Object>> realBookList(int bookNo) {
		// TODO Auto-generated method stub
		return selectList("books.realBookList",bookNo);
	}

	@Override
	public void bookDelete(int bookNo) {
		// TODO Auto-generated method stub
		update("books.bookDelete", bookNo);
	}

	@Override
	public int canDeleteBook(int bookNo) {
		// TODO Auto-generated method stub
		return selectOne("books.canDeleteBook",bookNo);
	}

	@Override
	public List<BooksVo> getDeletedBooks() {
		// TODO Auto-generated method stub
		return selectList("books.getDeletedBooks");
	}

	@Override
	public List<BooksVo> newBookList() {
		// TODO Auto-generated method stub
		return selectList("books.newBookList");
	}


	
}
