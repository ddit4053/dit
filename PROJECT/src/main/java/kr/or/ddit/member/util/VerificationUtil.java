package kr.or.ddit.member.util;

import java.util.Random;

public class VerificationUtil {
    
    // 인증 코드 생성 (6자리 숫자)
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 100000~999999 사이의 숫자
        return String.valueOf(code);
    }
    
    // 개발용: 실제 이메일 발송 대신 콘솔에 출력 (테스트 목적)
    public static boolean sendVerificationCode(String email, String code, String purpose) {
        System.out.println("==== 인증 코드 발송 (개발용) ====");
        System.out.println("수신자: " + email);
        System.out.println("목적: " + purpose);
        System.out.println("인증 코드: " + code);
        System.out.println("==============================");
        return true;
    }
}