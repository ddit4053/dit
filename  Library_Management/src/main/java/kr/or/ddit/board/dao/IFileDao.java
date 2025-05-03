package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.vo.File_StorageVo;

/**
 * 파일 관련 데이터 액세스 객체 인터페이스
 */
public interface IFileDao {
    
    /**
     * 파일 그룹 번호로 파일 목록 조회
     * @param fileGroupNum 파일 그룹 번호
     * @return 파일 목록
     */
    List<File_StorageVo> selectFilesByGroupNum(int fileGroupNum);
    
    /**
     * 파일 번호로 파일 정보 조회
     * @param fileNo 파일 번호
     * @return 파일 정보
     */
    File_StorageVo selectFileByNo(int fileNo);
    
    int updateFileReference(File_StorageVo file);
    
    /**
     * 새 파일 등록
     * @param file 파일 정보
     * @return 영향받은 행 수
     */
    int insertFile(File_StorageVo file);
    
    /**
     * 파일 삭제
     * @param fileNo 파일 번호
     * @return 영향받은 행 수
     */
    int deleteFile(int fileNo);
    
    /**
     * 파일 그룹에 속한 모든 파일 삭제
     * @param fileGroupNum 파일 그룹 번호
     * @return 영향받은 행 수
     */
    int deleteFilesByGroupNum(int fileGroupNum);
    
    /**
     * 새 파일 그룹 생성
     * @return 생성된 파일 그룹 번호
     */
    int createFileGroup();
    
    /**
     * 미사용 파일 그룹 번호 목록 조회
     * @return 미사용 파일 그룹 번호 목록
     */
    List<Integer> findUnusedFileGroups();
    
    
    /**
     * 파일 그룹 삭제
     * @param fileGroupNum 삭제할 파일 그룹 번호
     * @return 영향받은 행 수
     */
    int deleteFileGroup(int fileGroupNum);
    
    /**
     * 삭제된 지 일정 기간이 지난 파일 조회
     * @param daysOld 삭제 후 경과 일수
     * @return 삭제할 파일 목록
     */
    List<File_StorageVo> selectFilesToPurge(int daysOld);

    /**
     * 삭제된 지 일정 기간이 지난 파일 메타데이터 물리적 삭제
     * @param daysOld 삭제 후 경과 일수
     * @return 영향받은 행 수
     */
    int purgeDeletedFiles(int daysOld);    
    
}