package kr.or.ddit.file.dao;

import kr.or.ddit.file.vo.FileVo;
import kr.or.ddit.util.MybatisDao;

public class FileDao extends MybatisDao{

	private static FileDao insatance;

	private FileDao() {

	}

	public static FileDao getInstance() {
		if (insatance == null) {
			insatance = new FileDao();
		}
		return insatance;
	}

	
	public FileVo download(FileVo file) {
		return selectOne("file.download", file);
	}

	public void upload(FileVo file) {
		update("file.upload", file);
	}
	
	public int getFileNo() {
		return selectOne("file.getFileNo");
	}
	
	
}
