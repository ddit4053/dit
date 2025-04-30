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
		List<File_StorageVo> fileList = null;
		return fileList = selectList("file.selectFilesByGroupNum", fileGroupNum);
	}

	@Override
	public File_StorageVo selectFileByNo(int fileNo) {
		return selectOne("file.selectFileByNo", fileNo);
	}

	@Override
	public int insertFile(File_StorageVo file) {
		return insert("file.insertFile", file);
	}

	@Override
	public int deleteFile(int fileNo) {
		return delete("file.deleteFile", fileNo);
	}

	@Override
	public int deleteFilesByGroupNum(int fileGroupNum) {
		return delete("file.deleteFilesByGroupNum", fileGroupNum);
	}

	@Override
	public int createFileGroup() {
		return insert("file.createFileGroup", null);
	}

	
	

}
