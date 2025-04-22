package kr.or.ddit.vo;

import lombok.Data;

@Data
public class File_StorageVo {
	private int fileNo;
	private String orgName;
	private String saveName;
	private String filePath;
	private int fileSize;
	private String fileType;
	private String uploadedDate;
	private String referenceType;
	private int referenceId;
	private int userNo;
	private int fileGroupNum;
}
