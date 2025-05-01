package kr.or.ddit.vo;

import lombok.Data;

@Data
public class File_StorageVo {
	private int fileNo;
	private String orgName;
	private String saveName;
	private String filePath;
	private int fileSize; // 2GB 이상 파일 처리시 long 타입으로 변경
	private String fileType;
	private String uploadedDate;
	private String referenceType; // 참조유형(예: "BOARD", "PROFILE" 등)
	private int referenceId; // 참조 ID (예: 게시글 번호)
	private int userNo;
	private int fileGroupNum;
}
