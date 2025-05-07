package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.FileGroupVo;
import kr.or.ddit.vo.File_StorageVo;

/**
 * 파일 관련 데이터 액세스 객체 인터페이스
 */
public interface IFileDao {
    
    /**
     * 파일 그룹으로 파일 목록 조회
     * @param fileGroup 파일 그룹 Vo
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
    
    FileGroupVo selectFileGroup(int fileGroupNum);
    
    int updateFileGroupCodeNo(FileGroupVo fileGroup);
    
    /**
     * 새 파일 등록
     * @param file 파일 정보
     * @return 영향받은 행 수
     */
    int insertFile(File_StorageVo file);
    
    // 임시 파일을 영구 파일로 업데이트
    int updateTempFiles(Map<String, Object> map);
    
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
    int createFileGroup(Map<String, Object> map);
    

}