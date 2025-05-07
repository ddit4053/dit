package kr.or.ddit.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 게시글 내용 처리를 위한 유틸리티 클래스
 * - HTML 태그가 없는 일반 텍스트의 줄바꿈을 p 태그로 변환
 * - figure 태그 등 HTML 태그가 있는 부분은 보존
 */
public class ContentPreprocessor {
    
    /**
     * 게시글 내용의 일반 텍스트 부분을 HTML p 태그로 변환하면서 기존 HTML 태그는 보존
     * @param content 원본 게시글 내용
     * @return HTML 태그로 처리된 게시글 내용
     */
    public static String preprocessContent(String content) {
        if (content == null) return "";
        
        // 이미 모든 내용이 HTML 태그로 되어 있는지 검사
        if (isAllHtmlContent(content)) {
            return content;
        }
        
        // HTML 태그를 보존하면서 일반 텍스트 부분만 p 태그로 변환
        StringBuilder result = new StringBuilder();
        
        // HTML 태그 패턴 (figure, iframe, div, a 등)
        Pattern tagPattern = Pattern.compile("<([a-z][a-z0-9]*)\\b[^>]*>.*?</\\1>|<[a-z][a-z0-9]*\\b[^>]*/>", Pattern.DOTALL);
        Matcher matcher = tagPattern.matcher(content);
        
        int lastEnd = 0;
        while (matcher.find()) {
            // HTML 태그 앞의 텍스트 처리
            String textBefore = content.substring(lastEnd, matcher.start());
            result.append(processPlainText(textBefore));
            
            // HTML 태그 자체는 그대로 추가
            result.append(matcher.group());
            
            lastEnd = matcher.end();
        }
        
        // 마지막 HTML 태그 이후의 텍스트 처리
        if (lastEnd < content.length()) {
            String remainingText = content.substring(lastEnd);
            result.append(processPlainText(remainingText));
        }
        
        return result.toString();
    }
    
    /**
     * 일반 텍스트의 줄바꿈을 p 태그로 변환
     * @param text 변환할 일반 텍스트
     * @return p 태그로 변환된 HTML
     */
    private static String processPlainText(String text) {
        if (text.trim().isEmpty()) {
            return "";
        }
        
        // 줄바꿈을 기준으로 분리하여 각 줄을 p 태그로 감싸기
        return Arrays.stream(text.split("\n"))
                .filter(line -> !line.trim().isEmpty())
                .map(line -> "<p data-ke-size=\"size16\">" + line.trim() + "</p>")
                .collect(Collectors.joining("\n"));
    }
    
    /**
     * 내용이 이미 모두 HTML 태그로 구성되어 있는지 확인
     * @param content 검사할 내용
     * @return HTML 태그로만 구성되어 있으면 true, 아니면 false
     */
    private static boolean isAllHtmlContent(String content) {
        // 내용에서 HTML 태그를 제거한 후 남은 텍스트가 공백만 있는지 확인
        String noHtmlContent = content.replaceAll("<[^>]*>", "").trim();
        return noHtmlContent.isEmpty();
    }
}