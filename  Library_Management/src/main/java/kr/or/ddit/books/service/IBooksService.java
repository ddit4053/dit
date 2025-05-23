package kr.or.ddit.books.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BooksVo;
import kr.or.ddit.vo.RealBookVo;
import kr.or.ddit.vo.ReviewsVo;

public interface IBooksService {
	public int insertBooks (BooksVo vo);

	public List<BooksVo> listBooks(Map<String, Object> map);

	public List<BooksVo> searchBookList(Map<String, Object> map);

	public BooksVo booksDetail(BooksVo bookvo);

	public List<BooksVo> mainSearchBookList(Map<String, Object> mapMain);

	public int countSearchBook(Map<String, Object> map);

	public int countMainSearchBook(Map<String, Object> map); // 메인 검색 결과 수

	public void insertBooksIfNotExist(BooksVo bookList);

	public List<Map<String, Object>> realBookList(int bookNo);

	public boolean deleteBook(int bookNo);

	public List<BooksVo> getDeletedBooks();

	public List<BooksVo> newBookList();

	public List<BooksVo> getNewBooksByPage(int page, int pageSize);

	public int insertRealBook();

	public List<Integer> getFavoriteGenres(int userNo);

	public List<BooksVo> getRecommendedBooks(List<Integer> favoriteGenres, int userNo);
	
}
