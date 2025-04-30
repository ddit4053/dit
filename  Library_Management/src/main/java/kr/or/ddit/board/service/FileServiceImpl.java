package kr.or.ddit.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import kr.or.ddit.board.dao.FileDaoImpl;
import kr.or.ddit.board.dao.IFileDao;
import kr.or.ddit.vo.File_StorageVo;

public class FileServiceImpl implements IFileService{
	
	private IFileDao fileDao;
	private static FileServiceImpl instance;

	private FileServiceImpl() {
		fileDao = FileDaoImpl.getInstance();
	}

	public static FileServiceImpl getInstance() {
		if (instance == null) {
			instance = new FileServiceImpl();
		}
		return instance;
	}

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
		}
		
		return false;
	}

	@Override
	public boolean deleteFilesByGroupNum(int fileGroupNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int createFileGroup() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<File_StorageVo> uploadFiles(HttpServletRequest request, String referenceType, int referenceId)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
