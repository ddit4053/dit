package kr.or.ddit.books.dao;

import kr.or.ddit.vo.BookFavoritesVo;

public interface IFavoriteDao {
	public void favoriteInsert(BookFavoritesVo favorVo);

	public int favoriteCheck(BookFavoritesVo favorVo);

	public void favoriteDelete(BookFavoritesVo favorVo);
}
