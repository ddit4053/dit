package kr.or.ddit.member.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	
	private static SqlSessionFactory sessionFactory;
	
	static {
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			// 최초 한번만 읽기 때문에 재시작해야 수정한것이 적용이 됨
			try {
				Reader rd = Resources.getResourceAsReader("config/mybatis-config.xml");
				sessionFactory = new SqlSessionFactoryBuilder().build(rd);
				rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static SqlSession getInstance() {
		return sessionFactory.openSession();
	}
	// 최종 결제가 성공하면 커밋되게 관리 : 한사이클 - 트랭잭션
	public static SqlSession getInstance(boolean autoCommit) {
		return sessionFactory.openSession(autoCommit);
	}
	
	
	
}
