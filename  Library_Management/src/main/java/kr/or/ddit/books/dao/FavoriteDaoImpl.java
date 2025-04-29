package kr.or.ddit.books.dao;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.BookFavoritesVo;

public class FavoriteDaoImpl extends MybatisDao implements IFavoriteDao {

	private static FavoriteDaoImpl instance;

	private FavoriteDaoImpl() {

	}

	public static FavoriteDaoImpl getInstance() {
		if (instance == null) {
			instance = new FavoriteDaoImpl();
		}
		return instance;
	}

	@Override
	public void farvoriteInsert(BookFavoritesVo favorVo) {
		// TODO Auto-generated method stub
		insert("bookFavorites.farvoriteInsert", favorVo);
	}



	

}
