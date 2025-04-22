package kr.or.ddit.file.vo;

import lombok.Data;

@Data
public class FileVo {
	private int fileNo;
	private String orgName;
	private String saveName;
	private String ext;
	private long fileSize;
	private String regDate;
	
	private String fileSizeStr;
	
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
		
		// fileSzieStr 값 만들어서 넣기
		
		String[] arr = {"Byte", "KB", "MB", "GB"};
		int n =0; 
		
		double temp = fileSize;
		
		while(temp>1024) {
			temp/=1024;
			n++;
		}
		
		temp = Math.floor(temp*10)/10;
		
		fileSizeStr = temp+arr[n];
		
		
		
	}
	
	
}
