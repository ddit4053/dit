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



	@Override
	public boolean deleteBook(int bookNo) {
		int count = booksDao.canDeleteBook(bookNo); //대여중인 책이 있는지 확인
		
	   if (count > 0) {
	        // 하나라도 대여중인 경우 삭제 불가
	        return false;
	    }
	   
	   	booksDao.bookDelete(bookNo);
		
		return true;
	}

	@Override
	public List<BooksVo> getDeletedBooks() {
		// TODO Auto-generated method stub
		return booksDao.getDeletedBooks();
	}

	@Override
	public List<BooksVo> newBookList() {
		// TODO Auto-generated method stub
		return booksDao.newBookList();
	}


	@Override
	public List<BooksVo> getNewBooksByPage(int page, int pageSize) {
        // 시작 행 번호와 끝 행 번호 계산
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;
        
		return booksDao.getNewBooksByPage(startRow, endRow);
	}

	
	

}
