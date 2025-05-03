package kr.or.ddit.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
    
    private static DataSource ds;
    private static final int TRANSACTION_TIMEOUT = 10; // 트랜잭션 타임아웃 (초)
    
    // 연결 정보 (Properties 파일에서 로드)
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    
    // 재시도 관련 상수
    private static final int MAX_CONNECTION_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000; // 1초 대기
    
    static {
        // db.properties 파일에서 연결 정보 로드 (MyBatis와 동일한 파일 사용)
        loadDbProperties();
        
        try {
            // JNDI를 통한 DataSource 접근
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/oracleDB");
            
            // JNDI 설정 확인 및 테스트 연결
            if (ds == null) {
                System.out.println("JNDI 설정을 찾을 수 없어 드라이버를 직접 로드합니다.");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } else {
                // 연결 테스트
                try (Connection testConn = ds.getConnection()) {
                    System.out.println("JNDI 데이터소스 연결 테스트 성공");
                } catch (SQLException e) {
                    System.out.println("JNDI 데이터소스 연결 테스트 실패: " + e.getMessage());
                    ds = null; // 실패 시 null로 설정하여 직접 연결 시도하도록 함
                }
            }
        } catch (Exception e) {
            System.out.println("DB 연결 초기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            try {
                // JNDI 실패 시 드라이버 직접 로드
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Oracle JDBC 드라이버를 찾을 수 없습니다.");
                ex.printStackTrace();
            }
        }
    }
    
    // db.properties 파일에서 연결 정보 로드 (MyBatis와 설정 공유)
    private static void loadDbProperties() {
        Properties props = new Properties();
        try (InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                props.load(is);
                DB_URL = props.getProperty("url");
                DB_USER = props.getProperty("user");
                DB_PASSWORD = props.getProperty("pass");
                System.out.println("DB 속성 파일 로드 성공: " + DB_URL);
            } else {
                System.out.println("db.properties 파일을 찾을 수 없습니다. 기본값을 사용합니다.");
                // 기본값 설정 (로그에 출력용)
                DB_URL = "속성 파일에서 URL을 로드하지 못했습니다";
                DB_USER = "속성 파일에서 사용자명을 로드하지 못했습니다";
                DB_PASSWORD = "속성 파일에서 비밀번호를 로드하지 못했습니다";
            }
        } catch (IOException e) {
            System.out.println("DB 속성 파일 로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 연결 획득 - 타임아웃 설정 및 재시도 로직 추가
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        SQLException lastException = null;
        
        for (int attempt = 0; attempt < MAX_CONNECTION_RETRIES; attempt++) {
            try {
                if (ds != null) {
                    conn = ds.getConnection(); // 커넥션 풀에서 연결 획득
                    System.out.println("JNDI 데이터소스에서 연결 획득 성공");
                } else {
                    // DataSource가 없는 경우 직접 연결 생성 (백업 방법)
                    System.out.println("직접 DB 연결 시도 #" + (attempt + 1));
                    conn = java.sql.DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    System.out.println("직접 DB 연결 성공");
                }
                
                // 연결 설정
                if (conn != null) {
                    // 자동 커밋 설정 (기본값)
                    conn.setAutoCommit(true);
                    
                    // 쿼리 타임아웃 설정
                    Statement stmt = conn.createStatement();
                    stmt.setQueryTimeout(TRANSACTION_TIMEOUT);
                    stmt.close();
                    
                    return conn; // 성공적으로 연결 획득
                }
            } catch (SQLException e) {
                lastException = e;
                System.out.println("DB 연결 시도 #" + (attempt + 1) + " 실패: " + e.getMessage());
                
                // 마지막 시도가 아니면 재시도
                if (attempt < MAX_CONNECTION_RETRIES - 1) {
                    try {
                        System.out.println(RETRY_DELAY_MS + "ms 후 재시도...");
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new SQLException("DB 연결 재시도 중 인터럽트 발생", ie);
                    }
                }
            }
        }
        
        // 모든 시도 실패 시
        throw new SQLException("최대 재시도 횟수(" + MAX_CONNECTION_RETRIES + ")에 도달했으나 연결 실패", lastException);
    }
    
    // 모든 리소스 해제 (Connection 포함)
    public static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("ResultSet 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("PreparedStatement 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (conn != null) {
            try {
                // 트랜잭션이 아직 활성화되어 있으면 롤백 (락 방지)
                if (!conn.getAutoCommit()) {
                    try {
                        conn.rollback();
                        conn.setAutoCommit(true);
                        System.out.println("자동으로 롤백되었습니다 - 미완료된 트랜잭션 감지");
                    } catch (SQLException ex) {
                        System.out.println("롤백 실패: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                conn.close(); // 커넥션 풀 사용 시 연결 반환
                System.out.println("DB 연결 반환 완료");
            } catch (SQLException e) {
                System.out.println("Connection 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    // 나머지 메소드는 그대로 유지...
    // 오버로딩된 메소드 - PreparedStatement와 ResultSet만 닫을 때
    public static void closeResources(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("ResultSet 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("PreparedStatement 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    // 트랜잭션 시작
    public static void startTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(false);
            System.out.println("트랜잭션 시작됨");
        }
    }
    
    // 트랜잭션 커밋
    public static void commitTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            conn.commit();
            conn.setAutoCommit(true); // 자동 커밋 복원
            System.out.println("트랜잭션 커밋됨");
        }
    }
    
    // 트랜잭션 롤백
    public static void rollbackTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            conn.rollback();
            conn.setAutoCommit(true); // 자동 커밋 복원
            System.out.println("트랜잭션 롤백됨");
        }
    }
    
    // 재시도 로직이 포함된 DB 작업 실행
    public static boolean executeWithRetry(DBOperation operation, int maxRetries) {
        int retries = 0;
        boolean success = false;
        long waitTime = 500; // 초기 대기 시간(밀리초)
        
        while (!success && retries < maxRetries) {
            Connection conn = null;
            try {
                System.out.println("DB 작업 실행 시도 #" + (retries + 1));
                conn = getConnection();
                startTransaction(conn);
                
                success = operation.execute(conn);
                
                if (success) {
                    commitTransaction(conn);
                    System.out.println("DB 작업 성공");
                } else {
                    rollbackTransaction(conn);
                    System.out.println("DB 작업 실패 (논리적 실패), 롤백됨");
                }
            } catch (SQLException e) {
                System.out.println("DB 작업 실행 중 예외 발생: " + e.getMessage());
                
                try {
                    if (conn != null) {
                        rollbackTransaction(conn);
                    }
                } catch (SQLException ex) {
                    System.out.println("롤백 중 예외 발생: " + ex.getMessage());
                    ex.printStackTrace();
                }
                
                // 데드락이나 타임아웃인 경우 재시도
                if (isDeadlockOrTimeout(e)) {
                    retries++;
                    System.out.println("데드락 또는 타임아웃 감지, " + retries + "번째 재시도...");
                    try {
                        // 지수 백오프: 재시도할 때마다 대기 시간 증가
                        Thread.sleep(waitTime);
                        waitTime *= 2; // 다음 대기 시간은 두 배로
                        System.out.println("다음 재시도까지 " + waitTime + "ms 대기");
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.out.println("대기 중 인터럽트 발생");
                        break;
                    }
                } else {
                    // 다른 오류는 즉시 실패 처리
                    System.out.println("데드락/타임아웃 이외의 SQL 예외 발생, 재시도하지 않음");
                    e.printStackTrace();
                    break;
                }
            } finally {
                closeResources(conn, null, null);
            }
        }
        
        if (!success && retries >= maxRetries) {
            System.out.println("최대 재시도 횟수(" + maxRetries + ")에 도달했으나 작업 실패");
        }
        
        return success;
    }
    
    // 데드락 또는 타임아웃 감지 메소드 개선
    private static boolean isDeadlockOrTimeout(SQLException e) {
        // Oracle 데드락 에러 코드: ORA-00060
        // 타임아웃 에러 코드: ORA-01013
        // 리소스 사용 불가: ORA-00054 (락 획득 실패)
        int errorCode = e.getErrorCode();
        String errorMsg = e.getMessage().toLowerCase();
        
        return errorCode == 60 || errorCode == 1013 || errorCode == 54 || 
               errorMsg.contains("deadlock") || 
               errorMsg.contains("timeout") || 
               errorMsg.contains("resource busy") ||
               errorMsg.contains("locked") ||
               errorMsg.contains("다른 프로세스에 의해 잠겨 있습니다");
    }
    
    // 데이터베이스 작업을 위한 함수형 인터페이스
    public interface DBOperation {
        boolean execute(Connection conn) throws SQLException;
    }
}