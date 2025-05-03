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
	public int updateFileReference(File_StorageVo file) {
	    return update("file.updateFileReference", file);
	}

	@Override
	public int insertFile(File_StorageVo file) {
		return insert("file.insertFile", file);
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
	
	// 논리적 삭제
	@Override
	public int deleteFileGroup(int fileGroupNum) {
	    return update("file.deleteFileGroup", fileGroupNum);
	}
	
	// 미사용 파일 그룹번호 조회
	@Override
	public List<Integer> findUnusedFileGroups() {
	    return selectList("file.findUnusedFileGroups");
	}	
	
	/**
	 * 삭제된 지 일정 기간이 지난 파일 조회
	 * @param daysOld 삭제 후 경과 일수
	 * @return 삭제할 파일 목록
	 */
	@Override
	public List<File_StorageVo> selectFilesToPurge(int daysOld) {
	    return selectList("file.selectFilesToPurge", daysOld);
	}

	/**
	 * 삭제된 지 일정 기간이 지난 파일 메타데이터 물리적 삭제
	 * @param daysOld 삭제 후 경과 일수
	 * @return 영향받은 행 수
	 */
	@Override
	public int purgeDeletedFiles(int daysOld) {
	    return delete("file.purgeDeletedFiles", daysOld);
	}
	
	@Override
	public int createFileGroup() {
		return insert("file.createFileGroup", null);
	}

	
	

}
