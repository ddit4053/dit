package kr.or.ddit.books.service;

import kr.or.ddit.books.dao.FavoriteDaoImpl;
import kr.or.ddit.books.dao.IFavoriteDao;
import kr.or.ddit.vo.BookFavoritesVo;

public class FavoriteServiceImpl implements IFavoriteService {
	private static FavoriteServiceImpl instance;
	IFavoriteDao favoriteDao = FavoriteDaoImpl.getInstance();
	private FavoriteServiceImpl() {

	}

	public static FavoriteServiceImpl getInstance() {
		if (instance == null) {
			instance = new FavoriteServiceImpl();
		}
		return instance;
	}

	@Override
	public void farvoriteInsert(BookFavoritesVo favorVo) {
		// TODO Auto-generated method stub
		favoriteDao.farvoriteInsert(favorVo);
	}

}
