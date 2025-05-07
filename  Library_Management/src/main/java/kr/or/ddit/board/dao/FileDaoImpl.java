package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.FileGroupVo;
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
	public int updateFileReference(File_StorageVo file) {
	    return update("file.updateFileReference", file);
	}

	@Override
	public int insertFile(File_StorageVo file) {
		return insert("file.insertFile", file);
	}
	
	// 임시 파일을 영구 파일로 업데이트
	@Override
	public int updateTempFiles(Map<String, Object> map) {
		return update("file.updateTempFiles", map);
	}
	
	// 파일번호로 파일 논리적 삭제
	@Override
	public int deleteFile(int fileNo) {
		return update("file.deleteFile", fileNo);
	}
	
	// 파일 그룹번호로 파일 논리적 삭제
	@Override
	public int deleteFilesByGroupNum(int fileGroupNum) {
		return update("file.deleteFilesByGroupNum", fileGroupNum);
	}
	
	@Override
	public int createFileGroup(Map<String, Object> map) {
		return insert("file.createFileGroup", map);
	}

	@Override
	public int updateFileGroupCodeNo(FileGroupVo fileGroup) {
		return update("file.updateFileGroupCodeNo", fileGroup);
	}

	@Override
	public FileGroupVo selectFileGroup(int fileGroupNum) {
		return selectOne("file.selectFileGroup", fileGroupNum);
	}

	
	

}
