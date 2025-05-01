package kr.or.ddit.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kr.or.ddit.board.dao.FileDaoImpl;
import kr.or.ddit.board.dao.IFileDao;
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
		// 실제 파일 삭제 처리
		File_StorageVo fileInfo = fileDao.selectFileByNo(fileNo);
		if (fileInfo != null) {
			File file = new File(fileInfo.getFilePath() + File.separator + fileInfo.getSaveName());
			if(file.exists()) {
				file.delete();
			}
		}
		
		// DB에서 파일 정보 삭제
		return fileDao.deleteFile(fileNo) > 0;
	}

	@Override
	public boolean deleteFilesByGroupNum(int fileGroupNum) {
		// 파일 그룹에 속한 모든 파일 정보 조회
		List<File_StorageVo> fileList = fileDao.selectFilesByGroupNum(fileGroupNum);
		
		// 실제 파일 삭제
		for (File_StorageVo fileInfo : fileList) {
			File file = new File(fileInfo.getFilePath() + File.separator + fileInfo.getSaveName());
			if (file.exists()) {
				file.delete();
			}
		}
		
		// DB에서 파일 정보 삭제
		return fileDao.deleteFilesByGroupNum(fileGroupNum) > 0;
	}

	@Override
	public int createFileGroup() {
		return fileDao.createFileGroup();
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
		// 파일 저장 경로 설정 (날짜 기반 폴더 구조)
		String rootPath = "D:/upload/"; // 실제 환경에 맞게 조정 필요
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String folder = sdf.format(new Date());
		
		// 폴더 생성
		File dirPath = new File(rootPath + folder);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
		// 세션에서 사용자 정보 가져오기
		HttpSession session = req.getSession();
		UsersVo loginUser = (UsersVo) session.getAttribute("user");
		int userNo = loginUser != null ? loginUser.getUserNo() : 0;
		
		// 새 파일 그룹 생성
		int fileGroupNum = createFileGroup();
		
		List<File_StorageVo> fileList = new ArrayList<>();
		
		// 업로드된 파일 처리
		// Part: 파일 내용, 헤더, 크기 등 파일 업로드와 관련된 정보에 접근
		// getParts(): multipart/form-data 형식으로 전송된 모든 Part를 컬렉션으로 반환
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
			String filePath = rootPath + folder;
			
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
            
            // 파일 저장
            part.write(filePath + File.separator + saveName);
            
            // DB에 파일 정보 저장
            insertFile(file);
            
            fileList.add(file);
		}
		return fileList;
	}
}