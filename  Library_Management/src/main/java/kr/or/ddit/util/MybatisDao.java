package kr.or.ddit.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class MybatisDao {

	// SELECT - list
	public <T> List<T> selectList(String statement) {
		List<T> list = null;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			list = session.selectList(statement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public <T> List<T> selectList(String statement, Object param) {
		List<T> list = null;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			list = session.selectList(statement, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SELECT - one
	public <T> T selectOne(String statement) {
		T result = null;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			result = session.selectOne(statement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public <T> T selectOne(String statement, Object param) {
		T result = null;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			result = session.selectOne(statement, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// INSERT
	public int insert(String statement, Object param) {
		int count = 0;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			count = session.insert(statement, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// UPDATE
	public int update(String statement, Object param) {
		int count = 0;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			count = session.update(statement, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// DELETE
	public int delete(String statement, Object param) {
		int count = 0;
		try (SqlSession session = MybatisUtil.getInstance(true)) {
			count = session.delete(statement, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
