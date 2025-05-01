package kr.or.ddit.books.service;

import kr.or.ddit.vo.BookFavoritesVo;

public interface IFavoriteService {
	public void favoriteInsert(BookFavoritesVo favorVo);

	public int favoriteCheck(BookFavoritesVo favorVo);

	public void favoriteDelete(BookFavoritesVo favorVo);
}
