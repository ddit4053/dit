package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.File_StorageVo;

public class FileDaoImpl extends MybatisDao implements IFileDao{
	
	private static FileDaoImpl instance;

	private FileDaoImpl() {

	}

	public static FileDaoImpl getInstance() {
		if (instance == null) {
			instance = new FileDaoImpl();
		}
		return instance;
	}

	@Override
	public List<File_StorageVo> selectFilesByGroupNum(int fileGroupNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File_StorageVo selectFileByNo(int fileNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertFile(File_StorageVo file) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFile(int fileNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFilesByGroupNum(int fileGroupNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createFileGroup() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

}
