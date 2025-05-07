package kr.or.ddit.board.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import kr.or.ddit.vo.FileGroupVo;
import kr.or.ddit.vo.File_StorageVo;

/**
 * 파일 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface IFileService {
    
    /**
     * 파일 그룹 번호로 파일 목록 조회
     * @param fileNo
     * @return 파일 목록
     */
    List<File_StorageVo> getFilesByGroupNum(int fileGroupNum);
    
    /**
     * 파일 번호로 파일 정보 조회
     * @param fileNo 파일 번호
     * @return 파일 정보
     */
    File_StorageVo getFileByNo(int fileNo);
    
    // 파일 referId 업데이트
    boolean updateFileReference(File_StorageVo file);
    
    // 파일 그룹 번호로 파일그룹 조회
    FileGroupVo selectFileGroup(int fileGroupNum);
    
    // 파일 그룹의 코드 넘버 업데이트
    boolean updateFileGroupCodeNo(FileGroupVo fileGroup);
    
    /**
     * 파일 다운로드 처리
     * @param file 파일 정보
     * @param outputStream 출력 스트림
     * @return 성공 여부
     */
    boolean downloadFile(File_StorageVo file, OutputStream outputStream);
    
    /**
     * 새 파일 등록
     * @param file 파일 정보
     * @return 성공 여부
     */
    boolean insertFile(File_StorageVo file);
    
    
    // 임시 파일을 영구 파일로 변환
    boolean updateTempFilesToPermanent(int tempGroupNum, int boardNo);
    
    /**
     * 파일 논리 삭제
     * @param fileNo 파일 번호
     * @return 성공 여부
     */
    boolean deleteFile(int fileNo);
    
    /**
     * 파일 그룹에 속한 모든 파일 논리 삭제
     * @param fileGroupNum 파일 그룹 번호
     * @return 성공 여부
     */
    boolean deleteFilesByGroupNum(int fileGroupNum);
    
    
    /**
     * 새 파일 그룹 생성
     * @return 생성된 파일 그룹 번호
     */
    int createFileGroup(int fileGroupNum, int codeNo);
    
    /**
     * 파일 업로드 처리
     * 
     * @param request 파일이 포함된 HTTP 요청
     * @param referenceType 참조 유형 (예: "BOARD", "PROFILE" 등)
     * @param referenceId 참조 ID (예: 게시글 번호)
     * @return 업로드된 파일 목록
     * @throws IOException
     * @throws ServletException
     */
    List<File_StorageVo> uploadFiles(HttpServletRequest request, String referenceType, int referenceId) throws IOException, ServletException;
}