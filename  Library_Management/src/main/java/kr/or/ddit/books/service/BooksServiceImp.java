package kr.or.ddit.books.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.or.ddit.books.dao.BooksDaoImpl;
import kr.or.ddit.books.dao.IBooksDao;
import kr.or.ddit.vo.BooksVo;
import kr.or.ddit.vo.RealBookVo;
import kr.or.ddit.vo.ReviewsVo;

public class BooksServiceImp implements IBooksService{
	private static BooksServiceImp insatance;

	private IBooksDao booksDao = BooksDaoImpl.getInsatance();
	
	private BooksServiceImp() {

	}

	public static BooksServiceImp getInsatance() {
		if (insatance == null) {
			insatance = new BooksServiceImp();
		}
		return insatance;
	}

	@Override
	public int insertBooks(BooksVo vo) {
		// TODO Auto-generated method stub
		return booksDao.insertBooks(vo);
	}

	@Override
	public List<BooksVo> listBooks(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return booksDao.listBooks(map);
	}

	@Override
	public List<BooksVo> searchBookList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return booksDao.searchBookList(map);
	}

	@Override
	public BooksVo booksDetail(BooksVo bookvo) {
		// TODO Auto-generated method stub
		return booksDao.booksDetail(bookvo);
	}

	@Override
	public List<BooksVo> mainSearchBookList(Map<String, Object> mapMain) {
		// TODO Auto-generated method stub
		return booksDao.mainSearchBookList(mapMain);
	}

	@Override
	public int countSearchBook(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return booksDao.countSearchBook(map);
	}

	@Override
	public int countMainSearchBook(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return booksDao.countMainSearchBook(map);
	}

	@Override
	public void insertBooksIfNotExist(BooksVo book) { //isbn비교해서 삽입
		List<BooksVo> isbnList = booksDao.bookIsbnList(); // db에서 isbn 가져옴
		Set<String> isbnSet = new HashSet(); // set에 담아 중복체크
		
		 for (BooksVo vo : isbnList) {
		        isbnSet.add(vo.getIsbn());
		    }
		
		
			if(!isbnSet.contains(book.getIsbn())) {
				booksDao.insertBooks(book);
				isbnSet.add(book.getIsbn());
			}
		
		// TODO Auto-generated method stub
	
	}

	@Override
	public List<Map<String, Object>> realBookList(int bookNo) {
		// TODO Auto-generated method stub
		return booksDao.realBookList(bookNo);
	}


	

}
