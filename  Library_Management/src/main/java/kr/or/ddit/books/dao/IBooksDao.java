package kr.or.ddit.books.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BooksVo;

public interface IBooksDao {
	public int insertBooks (BooksVo vo);
	
	public List<BooksVo> listBooks();
	
	public List<BooksVo> searchBookList(Map<String, Object> map);
	
	public BooksVo booksDetail(BooksVo bookvo);

	public List<BooksVo> mainSearchBookList(Map<String, Object> mapMain);

	public int countSearchBook(Map<String, Object> map);
	
	public int countMainSearchBook(Map<String, Object> map);
}
