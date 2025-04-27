package kr.or.ddit.books.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.books.dao.BooksDaoImpl;
import kr.or.ddit.books.dao.IBooksDao;
import kr.or.ddit.vo.BooksVo;

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
	public List<BooksVo> listBooks() {
		// TODO Auto-generated method stub
		return booksDao.listBooks();
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


	

}
