package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.file.vo.FileVo;

public class FileUploadUtil {

	private static FileService fileService = FileService.getInstance();
	static String rootPath = "D:/upload/";
	
	public static List<FileVo> uploadUitl(HttpServletRequest req) throws IOException, ServletException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		Date date = new Date();
		
		String folder =  sdf.format(date);
		File f = new File(rootPath+folder);
		if(!f.exists()) f.mkdirs();
		
		
		List<FileVo> fileList = new ArrayList<FileVo>();
		
		for(Part part : req.getParts()) {
			String orgName = part.getSubmittedFileName();
			if(orgName == null || orgName.isEmpty()) {
				continue;
			}
			FileVo file = new FileVo();
			int fileNo = fileService.getFileNo();
			String[] temp = orgName.split("\\.");
			String ext = temp[temp.length-1];
			UUID uuid = UUID.randomUUID();
			String saveName = folder+"/"+uuid.toString(); 
			long size = part.getSize();
			
			file.setFileNo(fileNo);
			file.setOrgName(orgName);
			file.setSaveName(saveName);
			file.setExt(ext);
			file.setFileSize(size);
			
			fileList.add(file);
			
			fileService.upload(file);
			
			String name = part.getName();
			
			System.out.println(file);
			
			part.write(rootPath+saveName);
		}
		return fileList;
	}
	
}
