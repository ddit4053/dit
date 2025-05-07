package kr.or.ddit.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kr.or.ddit.board.dao.FileDaoImpl;
import kr.or.ddit.board.dao.IFileDao;
import kr.or.ddit.vo.FileGroupVo;
import kr.or.ddit.vo.File_StorageVo;
import kr.or.ddit.vo.UsersVo;

public class FileServiceImpl implements IFileService{
	
	private static FileServiceImpl instance;

	private FileServiceImpl() {

	}

	public static FileServiceImpl getInstance() {
		if (instance == null) {
			instance = new FileServiceImpl();
		}
		return instance;
	}
	
	private IFileDao fileDao = FileDaoImpl.getInstance();

	@Override
	public List<File_StorageVo> getFilesByGroupNum(int fileGroupNum) {
		return fileDao.selectFilesByGroupNum(fileGroupNum);
	}

	@Override
	public File_StorageVo getFileByNo(int fileNo) {
		return fileDao.selectFileByNo(fileNo);
	}
	
	@Override
	public boolean updateFileReference(File_StorageVo file) {
	    return fileDao.updateFileReference(file) > 0;
	}

	@Override
	public boolean downloadFile(File_StorageVo fileInfo, OutputStream outputStream) {
		File file = new File(fileInfo.getFilePath() + File.separator + fileInfo.getSaveName());
		
		if(!file.exists()) {
			return false;
		}
		
		try (FileInputStream fis = new FileInputStream(file)){
			byte[] buffer = new byte[4096]; // 한번에 읽어들일 buffer byte 사이즈 설정
			int bytesRead = 0;
			
			while ((bytesRead = fis.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush(); // 파라미터로 전달받은 외부객체이므로 flush 처리
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insertFile(File_StorageVo file) {
		return fileDao.insertFile(file) > 0; // DB insert 성공일 때만 true 반환
	} 
	
	
	@Override
	public boolean deleteFile(int fileNo) {
	    // 논리적 삭제만 처리 - 실제 파일은 삭제하지 않음
	    return fileDao.deleteFile(fileNo) > 0;
	}

	@Override
	public boolean deleteFilesByGroupNum(int fileGroupNum) {
	    // 논리적 삭제만 처리 - 실제 파일은 삭제하지 않음
	    return fileDao.deleteFilesByGroupNum(fileGroupNum) > 0;
	}
	
	/**
     * 게시글 번호와 동일한 파일그룹 번호를 생성하는 메서드
     * @param fileGroupNum 게시글 번호와 일치할 파일그룹 번호
     * @param codeNo 게시판 코드 번호
     * @return 생성된 파일그룹 번호 (게시글 번호와 동일)
     */	
	@Override
	public int createFileGroup(int fileGroupNum, int codeNo) {
		// 게시글 번호와 동일한 파일 그룹 번호를 가진 파일 그룹 생성
		Map<String, Object> map = new HashMap<>();
		map.put("fileGroupNum", fileGroupNum);
		map.put("codeNo", codeNo);
		fileDao.createFileGroup(map);
		return fileGroupNum;
	}
	
	
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
	@Override
	public List<File_StorageVo> uploadFiles(HttpServletRequest req, String referenceType, int referenceId)
			throws IOException, ServletException {
		// 파일 저장 경로 설정 (웹 애플리케이션 컨텍스트 내)
		String rootPath = req.getServletContext().getRealPath("/uploads");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String folder = sdf.format(new Date());
		
		// 폴더 생성
		File dirPath = new File(rootPath + File.separator + folder);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
		// 디버그용 로그 추가
		System.out.println("파일 업로드 경로: " + dirPath.getAbsolutePath());
		
		// 세션에서 사용자 정보 가져오기
		HttpSession session = req.getSession();
		UsersVo loginUser = (UsersVo) session.getAttribute("user");
		int userNo = loginUser != null ? loginUser.getUserNo() : 0;
		
	    // 파일 그룹 번호 설정
	    int fileGroupNum;
	    
	    // 임시 파일인 경우 특별한 처리
	    if (referenceId == -1) {
	        // 임시 파일을 위한 파일 그룹 번호 생성
	        String sessionId = session.getId();
	        // 세션 ID를 기반으로 임시 파일 그룹 번호 생성 (해시 등 사용)
	        fileGroupNum = Math.abs(sessionId.hashCode());
	        
	        // 해당 파일 그룹이 이미 존재하는지 확인
	        FileGroupVo existingGroup = fileDao.selectFileGroup(fileGroupNum);
	        
	        // 파일 그룹이 존재하지 않으면 생성
	        if (existingGroup == null) {
	            // 첫 번째 게시판(CODE_NO=1)으로 임시 파일 그룹 생성
	            try {
	                createFileGroup(fileGroupNum, 1);
	                System.out.println("임시 파일 그룹 생성 성공: " + fileGroupNum);
	            } catch (Exception e) {
	                System.out.println("임시 파일 그룹 생성 실패: " + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	        
	        System.out.println("임시 파일 처리: 세션 ID 기반 파일 그룹 번호 생성: " + fileGroupNum);
	    } else {
	        // 게시글의 번호를 파일그룹 번호로 사용 (게시글 ID와 파일그룹 ID를 동일하게 유지)
	        fileGroupNum = referenceId;
	    }
		
		List<File_StorageVo> fileList = new ArrayList<>();
		
		// 업로드된 파일 처리
		for (Part part : req.getParts()) {
			String orgName = part.getSubmittedFileName();
			
			// 파일이 아닌 경우 스킵
			if (orgName == null || orgName.isEmpty()) {
				continue;
			}
			
			// 파일 확장자 추출
			String fileType = "";
			int lastDotIndex = orgName.lastIndexOf(".");
			if (lastDotIndex > 0) {
				fileType = orgName.substring(lastDotIndex + 1).toLowerCase(); 
			}
			
			// UUID를 사용한 고유 파일명 생성
			UUID uuid = UUID.randomUUID();
			String saveName = uuid.toString() + (fileType.isEmpty() ? "" : "." + fileType);
			String filePath = rootPath + File.separator + folder;
			
			// 파일 정보 객체 생성
			File_StorageVo file = new File_StorageVo();
			file.setOrgName(orgName);
			file.setSaveName(saveName);
			file.setFilePath(filePath);
			file.setFileSize((int)part.getSize());
			file.setFileType(fileType);
            file.setReferenceType(referenceType);
            file.setReferenceId(referenceId);
            file.setUserNo(userNo);
            file.setFileGroupNum(fileGroupNum);
            
            // 임시 파일 여부 설정 (VO에 isTemp 필드가 있는 경우)
            if (referenceId == -1) {
                try {
                    // isTemp 필드를 직접 호출할 수 있다면 사용
                    file.setIsTemp("Y");
                    System.out.println("임시 파일로 설정: " + orgName);
                } catch (Exception e) {
                    // 리플렉션을 사용한 방법(필요한 경우)
                    try {
                        java.lang.reflect.Method method = file.getClass().getMethod("setIsTemp", String.class);
                        method.invoke(file, "Y");
                        System.out.println("임시 파일로 설정(리플렉션): " + orgName);
                    } catch (Exception ex) {
                        System.out.println("isTemp 필드가 없습니다. 필요하다면 VO 클래스에 추가하세요: " + ex.getMessage());
                    }
                }
            }
            
            try {
                // 파일 저장
                File saveFile = new File(filePath + File.separator + saveName);
                part.write(saveFile.getAbsolutePath());
                
                // 파일 저장 확인
                if (!saveFile.exists()) {
                    System.out.println("파일 저장 실패: " + saveFile.getAbsolutePath());
                } else {
                    System.out.println("파일 저장 성공: " + saveFile.getAbsolutePath());
                }
                
                // DB에 파일 정보 저장
                boolean inserted = insertFile(file);
                if (inserted) {
                    fileList.add(file);
                    System.out.println("파일 정보 DB 저장 성공: " + orgName);
                } else {
                    System.out.println("파일 정보 DB 저장 실패: " + orgName);
                }
            } catch (Exception e) {
                System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return fileList;
    }
	
	@Override
	public boolean updateTempFilesToPermanent(int tempGroupNum, int boardNo) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("tempGroupNum", tempGroupNum);
	    map.put("boardNo", boardNo);
	    
	    System.out.println("임시 파일 업데이트 요청: tempGroupNum=" + tempGroupNum + ", boardNo=" + boardNo);
	    
	    // 임시 파일 업데이트
	    int updatedCount = fileDao.updateTempFiles(map);
	    System.out.println("임시 파일 업데이트 완료: " + updatedCount + "개 파일 업데이트됨");
	    
	    return updatedCount > 0;
	}

	@Override
	public boolean updateFileGroupCodeNo(FileGroupVo fileGroup) {
		return fileDao.updateFileGroupCodeNo(fileGroup) > 0;
	}

	@Override
	public FileGroupVo selectFileGroup(int fileGroupNum) {
		return fileDao.selectFileGroup(fileGroupNum);
	}
	
}